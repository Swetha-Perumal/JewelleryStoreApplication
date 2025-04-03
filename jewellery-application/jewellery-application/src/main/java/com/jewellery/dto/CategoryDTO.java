package com.jewellery.dto;

import com.jewellery.model.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
	private Long categoryId;
	private String categoryName;
	private Product product;
}
