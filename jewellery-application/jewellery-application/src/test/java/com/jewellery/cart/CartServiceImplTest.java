package com.jewellery.cart;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import com.jewellery.exception.ResourceNotFoundException;
import com.jewellery.model.Cart;
import com.jewellery.model.CartItem;
import com.jewellery.model.Product;
import com.jewellery.repository.CartItemRepository;
import com.jewellery.repository.CartRepository;
import com.jewellery.repository.ProductRepository;
import com.jewellery.service.CartServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    private Cart cart;
    private Product product;
    private CartItem cartItem;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        cart.setCartId(1L);
        cart.setCartItems(new ArrayList<>()); // Initialize cartItems list

        product = new Product();
        product.setProductId(1L);
        product.setProductName("Gold Ring");
        product.setPrice(500.0);
        product.setQuantity(10);

        cartItem = new CartItem();
        cartItem.setCartItemId(1L);
        cartItem.setCart(cart); // Use setCart()
        cartItem.setProduct(product); // Use setProduct()
        cartItem.setQuantity(2);
        cartItem.setTotalPrice(1000.0);

        cart.getCartItems().add(cartItem); // Add cartItem to cart
    }

    @Test
    void testAddItemToCart_Success() {
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        CartItem result = cartService.addItemToCart(1L, 1L, 2);

        assertNotNull(result);
        assertEquals(2, result.getQuantity());
        assertEquals(1000.0, result.getTotalPrice());
        verify(cartItemRepository, times(1)).save(any(CartItem.class));
    }

    @Test
    void testAddItemToCart_ThrowsCartNotFoundException() {
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartService.addItemToCart(1L, 1L, 2);
        });

        assertEquals("Cart not found with ID: 1", exception.getMessage());
    }

    @Test
    void testAddItemToCart_ThrowsProductNotFoundException() {
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartService.addItemToCart(1L, 1L, 2);
        });

        assertEquals("Product not found with ID: 1", exception.getMessage());
    }

    @Test
    void testAddItemToCart_ThrowsNotEnoughStockException() {
        product.setQuantity(1);
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartService.addItemToCart(1L, 1L, 2);
        });

        assertEquals("Not enough stock available for product: Gold Ring", exception.getMessage());
    }

    @Test
    void testDeleteItemFromCart_Success() {
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findById(1L)).thenReturn(Optional.of(cartItem));

        cartService.deleteItemFromCart(1L, 1L);

        verify(cartItemRepository, times(1)).delete(cartItem);
    }

    @Test
    void testDeleteItemFromCart_ThrowsCartNotFoundException() {
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartService.deleteItemFromCart(1L, 1L);
        });

        assertEquals("Cart not found with ID: 1", exception.getMessage());
    }

    @Test
    void testDeleteItemFromCart_ThrowsCartItemNotFoundException() {
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartService.deleteItemFromCart(1L, 1L);
        });

        assertEquals("Cart Item not found with ID: 1", exception.getMessage());
    }

    @Test
    void testGetCartItemsByCartId_Success() {
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

        List<CartItem> cartItems = cartService.getCartItemsByCardId(1L);

        assertNotNull(cartItems);
        assertEquals(1, cartItems.size());
        assertEquals(cartItem, cartItems.get(0));
    }

    @Test
    void testGetCartItemsByCartId_ThrowsCartNotFoundException() {
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartService.getCartItemsByCardId(1L);
        });

        assertEquals("Cart not found with ID: 1", exception.getMessage());
    }

    @Test
    void testCalculateTotalPrice_Success() {
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

        double totalPrice = cartService.calculateTotalPrice(1L);

        assertEquals(1000.0, totalPrice);
    }

    @Test
    void testCalculateTotalPrice_ThrowsCartNotFoundException() {
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartService.calculateTotalPrice(1L);
        });

        assertEquals("Cart not found with ID: 1", exception.getMessage());
    }
}
