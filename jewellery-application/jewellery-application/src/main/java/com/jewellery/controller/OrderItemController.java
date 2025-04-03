package com.jewellery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jewellery.model.OrderItem;
import com.jewellery.service.OrderItemServiceImpl;

@RestController
@RequestMapping("/orderItem")
public class OrderItemController {
	
	@Autowired
	OrderItemServiceImpl orderItemServiceImpl;


    @GetMapping("/{orderId}")
    public ResponseEntity<List<OrderItem>> getOrderItemsByOrderId(@PathVariable Long orderId) {
        List<OrderItem> orderItems = orderItemServiceImpl.getOrderItemsByOrderId(orderId);
        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }


	
}



































//@PostMapping("/{orderId}/add")
//public ResponseEntity<OrderItem> addOrderItem(@PathVariable Long orderId, @RequestBody OrderItemsDTO orderItemDTO) {
//  OrderItem createdOrderItem = orderItemServiceImpl.addOrderItem(orderId, orderItemDTO);
//  return new ResponseEntity<>(createdOrderItem, HttpStatus.CREATED);
//}
//


//@DeleteMapping("/{orderItemId}/remove")
//public ResponseEntity<Void> removeOrderItem(@PathVariable Long orderItemId) {
//  orderItemServiceImpl.removeOrderItem(orderItemId);
//  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//}


//@PutMapping("/{orderItemId}/update")
//public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long orderItemId, @RequestBody OrderItemsDTO orderItemDTO) {
//  OrderItem updatedOrderItem = orderItemServiceImpl.updateOrderItem(orderItemId, orderItemDTO);
//  return new ResponseEntity<>(updatedOrderItem, HttpStatus.OK);
//}
