package com.jewellery.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewellery.exception.ResourceNotFoundException;
import com.jewellery.model.Cart;
import com.jewellery.model.CartItem;
import com.jewellery.model.Product;
import com.jewellery.repository.CartItemRepository;
import com.jewellery.repository.CartRepository;
import com.jewellery.repository.ProductRepository;


@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartItemRepository cartItemsRepository;
	
	

    
    @Override
    public CartItem addItemToCart(Long cartId, Long productId, int quantity){
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with ID: " + cartId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));
        if (product.getQuantity() < quantity) {
            throw new ResourceNotFoundException("Not enough stock available for product: " + product.getProductName());
        }
        // Create new CartItem
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(product.getPrice() * quantity);
        // Save CartItem
        cartItemsRepository.save(cartItem);
        return cartItem;
    }

    @Override
    public void deleteItemFromCart(Long cartId, Long cartItemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with ID: " + cartId));

        CartItem cartItem = cartItemsRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart Item not found with ID: " + cartItemId));

	 if (!cart.getCartItems().contains(cartItem)) {
            throw new IllegalArgumentException("Cart Item with ID: " + cartItemId + " does not belong to Cart with ID: " + cartId);
        }
        cart.getCartItems().remove(cartItem);
        cartItemsRepository.delete(cartItem);
    }

    @Override
    public List<CartItem> getCartItemsByCardId(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with ID: " + cartId));

        return cart.getCartItems();
    }

    @Override
    public double calculateTotalPrice(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with ID: " + cartId));

        return cart.getCartItems().stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }

}
