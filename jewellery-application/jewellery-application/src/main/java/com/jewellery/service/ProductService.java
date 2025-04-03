package com.jewellery.service;

import java.util.List;

import com.jewellery.dto.ProductDTO;
import com.jewellery.model.Product;

public interface ProductService {

	public ProductDTO getProductById(Long productId);

	public ProductDTO updateProduct(ProductDTO productDTO, Long userId);

	public void deleteProduct(Long productId, Long userId);

	public Product addProduct(Product product, Long userId) ;

	public List<ProductDTO> getAllProducts();

	public List<ProductDTO> getProductsByCategoryName(String categoryName);
}
