package com.eve_coding.eveshop.repository;

import com.eve_coding.eveshop.model.CartItem;
import com.eve_coding.eveshop.model.ProductToCartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductToCartItemRepository extends JpaRepository<ProductToCartItem,Long> {

    void deleteByCartItem(CartItem cartItem);
}
