package com.jewellery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewellery.model.Cart;
import com.jewellery.model.CartItem;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	 List<CartItem> findByCart_CartId(Long cartId);
	 List<CartItem> findByCart(Cart cart);
	 CartItem findByCart_CartIdAndProduct_ProductId(Long cartId, Long productId);	
	
}
