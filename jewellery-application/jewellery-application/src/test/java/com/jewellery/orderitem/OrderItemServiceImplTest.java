package com.jewellery.orderitem;

import com.jewellery.model.OrderItem;
import com.jewellery.model.Orders;
import com.jewellery.repository.OrderItemRepository;
import com.jewellery.repository.OrderRepository;
import com.jewellery.service.OrderItemServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceImplTest {

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderRepository orderRepository;

    private Orders order;
    private OrderItem orderItem;

    @BeforeEach
    void setUp() {
        order = new Orders();
        order.setOrderId(1L);

        orderItem = new OrderItem();
        orderItem.setOrder(order);

    }

    @Test
    void testGetOrderItemsByOrderId_Success() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderItemRepository.findByOrder(order)).thenReturn(List.of(orderItem));

        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderId(1L);

        assertNotNull(orderItems);
        assertEquals(1, orderItems.size());
        assertEquals(order, orderItems.get(0).getOrder());

        verify(orderRepository, times(1)).findById(1L);
        verify(orderItemRepository, times(1)).findByOrder(order);
    }

    @Test
    void testGetOrderItemsByOrderId_OrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> orderItemService.getOrderItemsByOrderId(1L));
        assertEquals("Order not found", exception.getMessage());

        verify(orderRepository, times(1)).findById(1L);
        verify(orderItemRepository, never()).findByOrder(any());
    }
}

