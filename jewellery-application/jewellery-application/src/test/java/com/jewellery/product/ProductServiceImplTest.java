package com.jewellery.product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.jewellery.Utils.Role;
import com.jewellery.dto.ProductDTO;
import com.jewellery.exception.AuthorizationException;
import com.jewellery.exception.ResourceNotFoundException;
import com.jewellery.model.Product;
import com.jewellery.model.User;
import com.jewellery.repository.ProductRepository;
import com.jewellery.repository.UserRepository;
import com.jewellery.service.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {  

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private User vendorUser;
    private User customerUser;
    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        vendorUser = new User();
        vendorUser.setUserid(1L);  // ✅ Fixed field name
        vendorUser.setRole(Role.VENDOR);

        customerUser = new User();
        customerUser.setUserid(2L);
        customerUser.setRole(Role.CUSTOMER);

        product = new Product();
        product.setProductId(1L);
        product.setProductName("Gold Ring");
        product.setDescription("24K Gold Ring");
        product.setPrice(5000.0);
        product.setQuantity(10);
        product.setBrand("Tanishq");
        product.setMaterial("Gold");

        productDTO = new ProductDTO();
        productDTO.setProductId(1L);
        productDTO.setProductName("Gold Ring");
        productDTO.setDescription("24K Gold Ring");
        productDTO.setPrice(5000.0);
        productDTO.setQuantity(10);
        productDTO.setBrand("Tanishq");
        productDTO.setMaterial("Gold");
    }

    @Test
    void addProduct_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(vendorUser));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.addProduct(product, 1L);

        assertNotNull(savedProduct);
        assertEquals("Gold Ring", savedProduct.getProductName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void addProduct_ShouldThrowAuthorizationException() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(customerUser));

        assertThrows(AuthorizationException.class, () -> productService.addProduct(product, 2L));
    }

    @Test
    void updateProduct_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(vendorUser));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(productDTO);

        ProductDTO updatedProduct = productService.updateProduct(productDTO, 1L);

        assertNotNull(updatedProduct);
        assertEquals("Gold Ring", updatedProduct.getProductName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct_ShouldThrowAuthorizationException() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(customerUser));

        assertThrows(AuthorizationException.class, () -> productService.updateProduct(productDTO, 2L));
    }

    @Test
    void updateProduct_ShouldThrowResourceNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(vendorUser));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(productDTO, 1L));
    }

    @Test
    void deleteProduct_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(vendorUser));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(1L, 1L);

        verify(productRepository).delete(product);
    }

    @Test
    void deleteProduct_ShouldThrowAuthorizationException() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(customerUser));

        assertThrows(AuthorizationException.class, () -> productService.deleteProduct(1L, 2L));
    }

    @Test
    void deleteProduct_ShouldThrowResourceNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(vendorUser));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(1L, 1L));
    }

    @Test
    void getProductById_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(productDTO);

        ProductDTO result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Gold Ring", result.getProductName());
    }

    @Test
    void getProductById_ShouldThrowResourceNotFoundException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    void getAllProducts_Success() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));
        when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(productDTO);

        List<ProductDTO> result = productService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals("Gold Ring", result.get(0).getProductName());
    }

    @Test
    void getProductsByCategoryName_Success() {
        when(productRepository.findByCategory_CategoryName("Jewellery")).thenReturn(Arrays.asList(product));
        when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(productDTO);

        List<ProductDTO> result = productService.getProductsByCategoryName("Jewellery");

        assertEquals(1, result.size());
        assertEquals("Gold Ring", result.get(0).getProductName());
    }
}
