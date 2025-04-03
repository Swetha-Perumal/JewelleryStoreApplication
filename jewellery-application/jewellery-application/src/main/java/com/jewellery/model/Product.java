package com.jewellery.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	@Id
	private Long productId;
	
	@NotBlank(message = "Product name cannot be empty")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
	private String productName;
	
	@Min(value = 1, message = "Quantity must be at least 1")
	private int quantity;
	
	@Positive(message = "Weight must be a positive number")
	private double weight;
	
    @Positive(message = "Price must be a positive number")
	private double price;
    
    @NotBlank(message = "Description cannot be empty")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
	private String description;
    
    @NotBlank(message = "Brand cannot be empty")
	private String brand;
    
    @NotBlank(message = "Material cannot be empty")
	private String material;
	
	@ManyToOne
	@JoinColumn(name = "category_id" )
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "vendor_id", referencedColumnName = "customerId") // Ensure correct reference
	private Customer customer;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product" , cascade = CascadeType.ALL)
	private List<CartItem> cartItems;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product" , cascade = CascadeType.ALL)
	private List<OrderItem>orderItems;
	
	

	

}
