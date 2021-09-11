package com.eve_coding.eveshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.awt.print.Book;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductToCartItem {

    @Id
    @SequenceGenerator(
            name = "product_to_cart_item_sequence",
            sequenceName = "product_to_cart_item_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_to_cart_item_sequence"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_item_id")
    private CartItem cartItem;
}
