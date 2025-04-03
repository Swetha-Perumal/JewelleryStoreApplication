package com.jewellery.controller;

import com.jewellery.model.CartItem;
import com.jewellery.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

	 @Autowired
     private CartService cartService;



    @PostMapping("/{cartId}/add/{productId}/{quantity}")
    public ResponseEntity<CartItem> addItemToCart(@PathVariable Long cartId, @PathVariable Long productId, @PathVariable int quantity) {
        CartItem cartItem = cartService.addItemToCart(cartId, productId, quantity);
        return new ResponseEntity<>(cartItem,HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}/remove/{cartItemId}")
    public ResponseEntity<String> deleteItemFromCart(@PathVariable Long cartId, @PathVariable Long cartItemId) {
        cartService.deleteItemFromCart(cartId, cartItemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartItem>> getCartItemsByCartId(@PathVariable Long cartId) {
        List<CartItem> cartItems = cartService.getCartItemsByCardId(cartId);
        return new ResponseEntity<>(cartItems,HttpStatus.OK);
    }

    @GetMapping("/{cartId}/total")
    public ResponseEntity<Double> calculateTotalPrice(@PathVariable Long cartId) {
        double totalPrice = cartService.calculateTotalPrice(cartId);
        return new ResponseEntity<>(totalPrice,HttpStatus.OK);
    }
}
