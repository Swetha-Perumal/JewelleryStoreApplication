package com.jewellery.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate primary key
    private Long cartItemId;

    private int quantity; 
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "cart_id") 
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id") 
    private Product product;
}
