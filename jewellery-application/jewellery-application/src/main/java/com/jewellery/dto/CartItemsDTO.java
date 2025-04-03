package com.jewellery.dto;

import com.jewellery.model.Cart;
import com.jewellery.model.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemsDTO {

	private Long cartItemId;
	private int Quantity;
	private double totalPrice;
	private Cart cart;
	private Product product;
}
