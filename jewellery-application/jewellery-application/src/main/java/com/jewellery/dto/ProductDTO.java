package com.jewellery.dto;


import com.jewellery.model.Category;
import com.jewellery.model.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private Long productId;
	private String productName;
	private int quantity;
	private double weight;
	private double price;
	private String description;
	private String brand;
	private String material;
	
	private Customer customer;
	private Category category;

	
}
