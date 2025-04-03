package com.jewellery.service;

import com.jewellery.model.OrderItem;
import com.jewellery.model.Orders;
import com.jewellery.repository.OrderItemRepository;
import com.jewellery.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

   

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderItemRepository.findByOrder(order);
    }

}




