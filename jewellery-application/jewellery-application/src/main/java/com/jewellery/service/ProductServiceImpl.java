package com.jewellery.service;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewellery.Utils.Role;
import com.jewellery.dto.ProductDTO;
import com.jewellery.exception.AuthorizationException;
import com.jewellery.exception.ResourceNotFoundException;
import com.jewellery.model.Product;
import com.jewellery.model.User;
import com.jewellery.repository.ProductRepository;
import com.jewellery.repository.UserRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	UserRepository userRepository;
	
	

	@Override
	public Product addProduct(Product product, Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
	    if (!user.getRole().equals(Role.VENDOR)) {
	        throw new AuthorizationException("Only vendors can add Product.");
	    }
		return productRepository.save(product);
	}
	
	

	@Override
	public ProductDTO updateProduct(ProductDTO productDTO, Long userId) {
		User user = userRepository.findById(userId).
				orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		if (!user.getRole().equals(Role.VENDOR)) {
	        throw new AuthorizationException("Only vendors can update Product.");
	    }
		
		Product product = productRepository.findById(productDTO.getProductId())
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		
		product.setProductName(productDTO.getProductName());
		product.setQuantity(productDTO.getQuantity());
		product.setPrice(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());
		product.setBrand(productDTO.getBrand());
		product.setMaterial(productDTO.getMaterial());
		product.setPrice(productDTO.getPrice());
		
		return modelMapper.map(productRepository.save(product), ProductDTO.class);
	}
	
	

	@Override
	public void deleteProduct(Long productId, Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		if (!user.getRole().equals(Role.VENDOR)) {
	        throw new AuthorizationException("Only vendors can update Product.");
	    }
		
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		
		productRepository.delete(product);
	}
	
	
	@Override
	public ProductDTO getProductById(Long productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("ProductId is not found"));
		return modelMapper.map(product, ProductDTO.class);
	}
	

	@Override
	public List<ProductDTO> getAllProducts() {
		return productRepository.findAll().stream()
				.map(product -> modelMapper.map(product, ProductDTO.class))
				.toList();
	}

	
	@Override
	public List<ProductDTO> getProductsByCategoryName(String categoryName) {
		return productRepository.findByCategory_CategoryName(categoryName).stream()
				.map(product -> modelMapper.map(product, ProductDTO.class))
				.collect(Collectors.toList());
	}

}
