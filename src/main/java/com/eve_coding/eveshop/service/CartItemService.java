package com.eve_coding.eveshop.service;

import com.eve_coding.eveshop.model.*;
import com.eve_coding.eveshop.repository.CartItemRepository;
import com.eve_coding.eveshop.repository.ProductToCartItemRepository;
import com.eve_coding.eveshop.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductToCartItemRepository productToCartItemRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public List<CartItem> cartItems (ShoppingCart shoppingCart){
        return cartItemRepository.findAllByShoppingCart(shoppingCart);
    }

    public void addProductToCartItem(Product product, User user, int qty){

        List<CartItem> cartItems = cartItems(user.getShoppingCart());

        for(CartItem cartItem : cartItems){
            if(product.getProductId().equals(cartItem.getProduct().getProductId())){
                cartItem.setQty(cartItem.getQty() + qty);
                cartItem.setSubTotal(new BigDecimal(String.valueOf(product.getProductPrice())).multiply(new BigDecimal(qty)));
                cartItemRepository.save(cartItem);
                return;
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(user.getShoppingCart());
        cartItem.setProduct(product);
        cartItem.setQty(qty);
        cartItem.setSubTotal(new BigDecimal(String.valueOf(product.getProductPrice())).multiply(new BigDecimal(qty)));
        cartItemRepository.save(cartItem);

        ProductToCartItem productToCartItem = new ProductToCartItem();
        productToCartItem.setCartItem(cartItem);
        productToCartItem.setProduct(product);
        productToCartItemRepository.save(productToCartItem);

    }

    public void updateCartItemFromCart(CartItem cartItem, int qty){
        cartItem.setQty(qty);
        cartItemRepository.save(cartItem);
    }

    public void updateCartItem(CartItem cartItem) {
        BigDecimal bigDecimal;
        if(cartItem.getProduct().isOnSale()){
            bigDecimal = new BigDecimal(String.valueOf(cartItem.getProduct().getProductDiscountedPrice())).multiply(new BigDecimal(cartItem.getQty()));
        }else{
            bigDecimal = new BigDecimal(String.valueOf(cartItem.getProduct().getProductPrice())).multiply(new BigDecimal(cartItem.getQty()));
        }
        bigDecimal = bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
        cartItem.setSubTotal(bigDecimal);
        cartItemRepository.save(cartItem);
    }

    public CartItem getById(Long id){
        return cartItemRepository.getById(id);
    }

    public void removeCartItem(CartItem cartItem){
        productToCartItemRepository.deleteByCartItem(cartItem);
        cartItemRepository.delete(cartItem);
    }

    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }
}
