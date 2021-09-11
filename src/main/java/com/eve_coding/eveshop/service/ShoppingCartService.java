package com.eve_coding.eveshop.service;

import com.eve_coding.eveshop.model.CartItem;
import com.eve_coding.eveshop.model.ShoppingCart;
import com.eve_coding.eveshop.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemService cartItemService;

    public void updateShoppingCart(ShoppingCart shoppingCart){
        BigDecimal cartTotal = new BigDecimal(0);
        List<CartItem> cartItems = cartItemService.cartItems(shoppingCart);

        for(CartItem cartItem : cartItems){
            if(cartItem.getProduct().getProductQuantity() > 0){
                cartItemService.updateCartItem(cartItem);
                cartTotal = cartTotal.add(cartItem.getSubTotal());
            }
        }

        shoppingCart.setCartItemList(cartItems);
        shoppingCart.setGrandTotal(cartTotal);
        shoppingCartRepository.save(shoppingCart);

    }

    public void clearCart(ShoppingCart shoppingCart){
        List<CartItem> cartItems = shoppingCart.getCartItemList();

        for(CartItem cartItem : cartItems){
            cartItem.setShoppingCart(null);
            cartItemService.save(cartItem);
        }
        shoppingCart.setGrandTotal(new BigDecimal(0));
        shoppingCartRepository.save(shoppingCart);
    }

}
