package com.jewellery.service;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewellery.Utils.OrderStatus;
import com.jewellery.dto.AddressDTO;
import com.jewellery.dto.OrdersDTO;
import com.jewellery.feign.AddressClient;
import com.jewellery.model.Cart;
import com.jewellery.model.CartItem;
import com.jewellery.model.Customer;
import com.jewellery.model.Orders;
import com.jewellery.repository.CartRepository;
import com.jewellery.repository.OrderRepository;
import com.jewellery.security.JwtService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private AddressClient addressClient;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired 
	private JwtService jwtService;
	
	
	
	@Override
	public Orders placeOrder(Long customerId, Long addressId) {
	    Cart cart = cartRepository.findByCustomer_CustomerId(customerId);
	    if (cart == null || cart.getCartItems().isEmpty()) {
	        throw new RuntimeException("Cart is empty. Add products before placing an order.");
	    }

	    String token = "Bearer " + jwtService.generateToken(cart.getCustomer().getUser().getUserName());
	    AddressDTO shippingAddress =addressClient.getAddressById(addressId, token);
	    if (shippingAddress == null) {
	        throw new RuntimeException("Address not found");
	    }

	    // Create Order
	    Long maxOrderId = orderRepository.findMaxOrderId();
        Long newOrderId = (maxOrderId == null) ? 1L : maxOrderId + 1;
	    
	    Orders order = new Orders();
	    order.setOrderId(newOrderId);
	    order.setCustomer(cart.getCustomer());
	    order.setOrderDate(LocalDateTime.now());
	    order.setStatus(OrderStatus.PENDING);
	    order.setTotalPrice(cart.getCartItems().stream().mapToDouble(CartItem::getTotalPrice).sum());
	    order.setAddressId(addressId); // Ensure shipping address is set
	    order = orderRepository.save(order);

	    return order;
	}
	
	

	@Override
	public List<Orders> getOrdersByCustomerId(Long customerId) {
		return orderRepository.findByCustomer_CustomerId(customerId);
	}

	
	
	@Override
	public OrdersDTO getOrderById(Long orderId) {
	    Orders order = orderRepository.findById(orderId)
	            .orElseThrow(() -> new RuntimeException("Order not found"));

	    // Fetch the Address details using Feign Client
	    String token = "Bearer " + jwtService.generateToken(order.getCustomer().getUser().getUserName());
	    AddressDTO shippingAddress = addressClient.getAddressById(order.getAddressId(),token);

	    // Convert Orders to OrdersDTO
	    OrdersDTO orderDTO = new OrdersDTO();
	    orderDTO.setOrderId(order.getOrderId());
	    
	    orderDTO.setCustomer(new Customer()); // Ensure it's not null
	    orderDTO.getCustomer().setCustomerId(orderId);
	    
	    //orderDTO.getCustomer().setCustomerId(order.getCustomer().getCustomerId());
	    orderDTO.setOrderDate(order.getOrderDate());
	    orderDTO.setStatus(order.getStatus());
	    orderDTO.setTotalPrice(order.getTotalPrice());
	    orderDTO.setShippingAddress(shippingAddress); // Set full Address object

	    return orderDTO;
	}

	
	
	@Override
	public Orders updateOrderAddress(Long orderId, Long newAddressId) {
	    Orders order = orderRepository.findById(orderId)
	            .orElseThrow(() -> new RuntimeException("Order not found"));

	    // Restrict address change if the order is already shipped or delivered
	    if (order.getStatus() == OrderStatus.SHIPPED || order.getStatus() == OrderStatus.DELIVERED) {
	        throw new RuntimeException("Cannot change address after the order has been shipped or delivered.");
	    }
	    String token = "Bearer " + jwtService.generateToken(order.getCustomer().getUser().getUserName());

	    // Fetch new address from Address microservice using Feign Client
	    AddressDTO newAddress = addressClient.getAddressById(newAddressId, token);
	    if (newAddress == null) {
	        throw new RuntimeException("New address not found.");
	    }

	    // Update the shipping address
	    order.setAddressId(newAddressId);
	    return orderRepository.save(order);
	}


	@Override
	public List<Orders> getOrdersByStatus(OrderStatus status) {
		return orderRepository.findByStatus(status);
	}



}







