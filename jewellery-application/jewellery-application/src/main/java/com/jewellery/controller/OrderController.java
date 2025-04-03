package com.jewellery.controller;

import com.jewellery.Utils.OrderStatus;
import com.jewellery.dto.OrdersDTO;
import com.jewellery.model.Orders;
import com.jewellery.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/place/{customerId}/{addressId}")
    public ResponseEntity<Orders> placeOrder(@PathVariable Long customerId,@PathVariable Long addressId) {
        Orders order = orderService.placeOrder(customerId, addressId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Orders>> getOrdersByCustomerId(@PathVariable Long customerId) {
        List<Orders> orders = orderService.getOrdersByCustomerId(customerId);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<OrdersDTO> getOrderById(@PathVariable Long orderId) {
        OrdersDTO orderDTO = orderService.getOrderById(orderId);
        return new ResponseEntity<>(orderDTO,HttpStatus.OK);
    }


    @PutMapping("/{orderId}/address/{newAddressId}")
    public ResponseEntity<Orders> updateOrderAddress(@PathVariable Long orderId,@PathVariable Long newAddressId) {
        Orders updatedOrder = orderService.updateOrderAddress(orderId, newAddressId);
        return new ResponseEntity<>(updatedOrder,HttpStatus.OK);
    }


    @GetMapping("/status/{status}")
    public ResponseEntity<List<Orders>> getOrdersByStatus(@PathVariable OrderStatus status) {
        List<Orders> orders = orderService.getOrdersByStatus(status);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
}























//@PutMapping("/{orderId}/status/{status}")
//public ResponseEntity<Orders> updateOrderStatus(@PathVariable Long orderId,@PathVariable OrderStatus status) {
//  Orders updatedOrder = orderService.updateOrderStatus(orderId, status);
//  return new ResponseEntity<>(updatedOrder,HttpStatus.OK);
//}

