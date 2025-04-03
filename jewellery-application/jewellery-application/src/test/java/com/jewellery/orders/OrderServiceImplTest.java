package com.jewellery.orders;

import com.jewellery.Utils.OrderStatus;
import com.jewellery.dto.AddressDTO;
import com.jewellery.dto.OrdersDTO;
import com.jewellery.feign.AddressClient;
import com.jewellery.model.Cart;
import com.jewellery.model.CartItem;
import com.jewellery.model.Customer;
import com.jewellery.model.Orders;
import com.jewellery.model.User;
import com.jewellery.repository.CartRepository;
import com.jewellery.repository.OrderRepository;
import com.jewellery.security.JwtService;
import com.jewellery.service.OrderServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private AddressClient addressClient;

    @Mock
    private JwtService jwtService;

    private Orders order;
    private Customer customer;
    private Cart cart;
    private CartItem cartItem;
    private AddressDTO addressDTO;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setCustomerId(1L);
        
        User user = new User();
        user.setUserName("testuser");
        customer.setUser(user);

        cartItem = new CartItem();
        cartItem.setTotalPrice(100.0);

        cart = new Cart();
        cart.setCustomer(customer);
        cart.setCartItems(List.of(cartItem));

        addressDTO = new AddressDTO();
        addressDTO.setAddressId(1L);
        addressDTO.setCity("New York");

        order = new Orders();
        order.setOrderId(1L);
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalPrice(100.0);
        order.setAddressId(1L);
    }

    @Test
    void testPlaceOrder_Success() {
        when(cartRepository.findByCustomer_CustomerId(1L)).thenReturn(cart);
        when(jwtService.generateToken("testuser")).thenReturn("mockToken");
        when(addressClient.getAddressById(1L, "Bearer mockToken")).thenReturn(addressDTO);
        when(orderRepository.findMaxOrderId()).thenReturn(5L);
        when(orderRepository.save(any(Orders.class))).thenReturn(order);

        Orders placedOrder = orderService.placeOrder(1L, 1L);

        assertNotNull(placedOrder);
        assertEquals(OrderStatus.PENDING, placedOrder.getStatus());
        assertEquals(100.0, placedOrder.getTotalPrice());
        verify(orderRepository, times(1)).save(any(Orders.class));
    }



    @Test
    void testGetOrdersByCustomerId_Success() {
        when(orderRepository.findByCustomer_CustomerId(1L)).thenReturn(List.of(order));

        List<Orders> orders = orderService.getOrdersByCustomerId(1L);

        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals(1L, orders.get(0).getOrderId());
        verify(orderRepository, times(1)).findByCustomer_CustomerId(1L);
    }

    @Test
    void testGetOrderById_Success() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(jwtService.generateToken("testuser")).thenReturn("mockToken");
        when(addressClient.getAddressById(1L, "Bearer mockToken")).thenReturn(addressDTO);

        OrdersDTO result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getOrderId());
        assertEquals(100.0, result.getTotalPrice());
        assertEquals("New York", result.getShippingAddress().getCity());

        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testGetOrderById_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> orderService.getOrderById(1L));
        assertEquals("Order not found", exception.getMessage());

        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateOrderAddress_Success() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(jwtService.generateToken("testuser")).thenReturn("mockToken");
        when(addressClient.getAddressById(2L, "Bearer mockToken")).thenReturn(new AddressDTO());
        when(orderRepository.save(any(Orders.class))).thenReturn(order);

        Orders updatedOrder = orderService.updateOrderAddress(1L, 2L);

        assertNotNull(updatedOrder);
        assertEquals(2L, updatedOrder.getAddressId());
        verify(orderRepository, times(1)).save(any(Orders.class));
    }

    @Test
    void testUpdateOrderAddress_ShippedOrDelivered() {
        order.setStatus(OrderStatus.SHIPPED);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Exception exception = assertThrows(RuntimeException.class, () -> orderService.updateOrderAddress(1L, 2L));
        assertEquals("Cannot change address after the order has been shipped or delivered.", exception.getMessage());

        verify(orderRepository, never()).save(any());
    }

    @Test
    void testGetOrdersByStatus_Success() {
        when(orderRepository.findByStatus(OrderStatus.PENDING)).thenReturn(List.of(order));

        List<Orders> orders = orderService.getOrdersByStatus(OrderStatus.PENDING);

        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals(OrderStatus.PENDING, orders.get(0).getStatus());

        verify(orderRepository, times(1)).findByStatus(OrderStatus.PENDING);
    }

}










//@Test
//void testPlaceOrder_EmptyCart() {
//  when(cartRepository.findByCustomer_CustomerId(1L)).thenReturn(new Cart());
//
//  Exception exception = assertThrows(RuntimeException.class, () -> orderService.placeOrder(1L, 1L));
//  assertEquals("Cart is empty. Add products before placing an order.", exception.getMessage());
//
//  verify(orderRepository, never()).save(any());
//}
