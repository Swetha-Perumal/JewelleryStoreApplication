package com.jewellery.service;
import java.util.List;

import com.jewellery.model.CartItem;
public interface CartService {

	  
	  
	 public CartItem addItemToCart(Long cartId, Long productId, int quantity);

	 public void deleteItemFromCart(Long cartId, Long cartItemId);

	 public List<CartItem> getCartItemsByCardId(Long cartId);

	 public double calculateTotalPrice(Long cartId);

}
