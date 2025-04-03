package com.jewellery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jewellery.dto.ProductDTO;
import com.jewellery.model.Product;
import com.jewellery.service.ProductServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	ProductServiceImpl productServiceImpl;

	@PostMapping("/add/{userId}")
	public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product, @PathVariable Long userId) {
		Product createdProduct = productServiceImpl.addProduct(product, userId);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
	}
	

	@PutMapping("/Update/{userId}")
	public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO,@PathVariable Long userId) {
		ProductDTO updatedProduct = productServiceImpl.updateProduct(productDTO, userId);
        return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
	}

	@DeleteMapping("/{productId}/{userId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long productId, @PathVariable Long userId) {
		productServiceImpl.deleteProduct(productId, userId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	

	@GetMapping("/{productId}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) {
		ProductDTO product = productServiceImpl.getProductById(productId);
		return new ResponseEntity<>(product, HttpStatus.OK);
    }
	
    
	@GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productServiceImpl.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
	

	@GetMapping("/category/{categoryName}")
	public ResponseEntity<List<ProductDTO>> getProductByCategoryName(@PathVariable String categoryName) {
		List<ProductDTO> products = productServiceImpl.getProductsByCategoryName(categoryName);
        return new ResponseEntity<>(products, HttpStatus.OK);

	}
}
