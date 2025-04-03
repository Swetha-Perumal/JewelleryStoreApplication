package com.jewellery.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewellery.Utils.OrderStatus;
import com.jewellery.Utils.PaymentStatus;
import com.jewellery.dto.PaymentDTO;
import com.jewellery.exception.ResourceNotFoundException;
import com.jewellery.model.Cart;
import com.jewellery.model.OrderItem;
import com.jewellery.model.Orders;
import com.jewellery.model.Payment;
import com.jewellery.model.Product;
import com.jewellery.repository.CartRepository;
import com.jewellery.repository.OrderItemRepository;
import com.jewellery.repository.OrderRepository;
import com.jewellery.repository.PaymentRepository;
import com.jewellery.repository.ProductRepository;

@Service
public class PaymentServiceImpl implements PaymentService{
 
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	
	@Autowired
	CartRepository cartRepository;

	
	@Autowired	
	private ModelMapper modelMapper;

	
	@Override
	public List<PaymentDTO> getAllPayments() {
		List<Payment> payments = paymentRepository.findAll();
        return payments.stream()
                .map(payment -> modelMapper.map(payment, PaymentDTO.class))
                .collect(Collectors.toList());
	}
	
	
	@Override
	public PaymentDTO getPaymentById(Long paymentId) {
		Payment payment = paymentRepository.findById(paymentId)
	            .orElseThrow(() -> new ResourceNotFoundException("Address not found with id " + paymentId));
	    return modelMapper.map(payment, PaymentDTO.class);
 
	}

 
	@Override
	public List<Payment> getPaymentsByDateRange(Date startDate, Date endDate) {
		return paymentRepository.findByPaymentDateRange(startDate, endDate);
	}
	
	
	@Override
	public Payment getPaymentByOrderId(Long ordersId) {
		return paymentRepository.findByOrders_OrderId(ordersId);
	}
	
	
	
	@Override
	public Payment makePayment(Long orderId, String paymentMethod) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        
        Long maxPaymentId = paymentRepository.findMaxPaymentId();
        Long newPaymentId = (maxPaymentId == null) ? 1L : maxPaymentId + 1;
        
        Payment payment = new Payment();
        payment.setOrders(order);
        payment.setPaymentId(newPaymentId);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentAmount(order.getTotalPrice()); // Automatically set from order total price
        payment.setPaymentDate(new java.util.Date());
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        payment = paymentRepository.save(payment);

        // Convert CartItems to OrderItems only after successful payment
        Cart cart = cartRepository.findByCustomer_CustomerId(order.getCustomer().getCustomerId());
        if (cart != null) {
            List<OrderItem> orderItems = cart.getCartItems().stream().map(cartItem -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(cartItem.getTotalPrice());
                orderItem.setDeliveryDate(LocalDateTime.now().plusDays(5)); // Example delivery date
                
                Product product = cartItem.getProduct();
                product.setQuantity(product.getQuantity() - cartItem.getQuantity());
                productRepository.save(product);
                
                return orderItem;
            }).collect(Collectors.toList());

            orderItemRepository.saveAll(orderItems);
            order.setOrderItems(orderItems);
            order.setStatus(OrderStatus.PROCESSING);
            orderRepository.save(order);
            
            // Clear cart after successful payment
            cart.getCartItems().clear();
            cartRepository.save(cart);
        }

        return payment;
    }

}


