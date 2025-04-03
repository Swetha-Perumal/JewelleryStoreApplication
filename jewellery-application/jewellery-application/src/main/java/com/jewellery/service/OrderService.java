package com.jewellery.service;

import java.util.List;

import com.jewellery.Utils.OrderStatus;
import com.jewellery.dto.OrdersDTO;
import com.jewellery.model.Orders;

public interface OrderService {


	  public List<Orders> getOrdersByCustomerId(Long customerId);
	  
	  public  OrdersDTO getOrderById(Long orderId);
	  
	  public List<Orders> getOrdersByStatus(OrderStatus status);
	  
	  public Orders placeOrder(Long customerId, Long addressId);
	  
	  public Orders updateOrderAddress(Long orderId, Long newAddressId);
	  

}

