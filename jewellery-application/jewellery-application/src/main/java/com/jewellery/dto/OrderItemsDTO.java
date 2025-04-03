package com.jewellery.dto;

import java.time.LocalDateTime;

import com.jewellery.model.Orders;
import com.jewellery.model.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDTO {

	private Long orderId;
	private double price;
	private int quantity;
	private LocalDateTime deliveryDate;
	private Orders oder;
	private Product product;
}
