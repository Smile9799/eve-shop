package com.eve_coding.eveshop.repository;

import com.eve_coding.eveshop.model.CartItem;
import com.eve_coding.eveshop.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    List<CartItem> findAllByShoppingCart(ShoppingCart shoppingCart);
}
