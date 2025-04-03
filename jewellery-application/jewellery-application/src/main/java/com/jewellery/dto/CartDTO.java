package com.jewellery.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

	private Long cartId;
	 private CustomerDTO customer;
	private List<CartItemsDTO> cartItems; 
}
