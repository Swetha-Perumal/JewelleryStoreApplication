package com.jewellery.service;

import com.jewellery.model.OrderItem;

import java.util.List;

public interface OrderItemService {

   
	public List<OrderItem> getOrderItemsByOrderId(Long orderId);
	
}
