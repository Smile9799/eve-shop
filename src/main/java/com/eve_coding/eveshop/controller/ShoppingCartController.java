package com.eve_coding.eveshop.controller;

import com.eve_coding.eveshop.model.CartItem;
import com.eve_coding.eveshop.model.Product;
import com.eve_coding.eveshop.model.ShoppingCart;
import com.eve_coding.eveshop.model.User;
import com.eve_coding.eveshop.service.CartItemService;
import com.eve_coding.eveshop.service.ProductService;
import com.eve_coding.eveshop.service.ShoppingCartService;
import com.eve_coding.eveshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/addToCart", method = RequestMethod.GET)
    public String addToCart(@ModelAttribute("product") String productName,
                            @ModelAttribute("qty") String qty,
                            Model model, Principal principal){

        Product product = productService.product(productName);
        User user = userService.getUserByEmail(principal.getName());

        cartItemService.addProductToCartItem(product,user,Integer.parseInt(qty));
        return "redirect:/product?product="+product.getProductName();
    }

    @GetMapping("/addItem")
    public String addToCart(@RequestParam("product") String productName,
                            @RequestParam("page") String page,
                            Principal principal, Model model){
        Product product = productService.product(productName);
        User user = userService.getUserByEmail(principal.getName());
        cartItemService.addProductToCartItem(product,user,1);

        if(page.equals("home")){
            return "redirect:/";
        }else{
            return "redirect:/shop";
        }
    }

    @RequestMapping(value = "/cart",method = RequestMethod.GET)
    public String cart(Model model,Principal principal){

        User user = userService.getUserByEmail(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();
        List<CartItem> cartItems = cartItemService.cartItems(shoppingCart);
        shoppingCartService.updateShoppingCart(shoppingCart);
        if(cartItems.size() > 0){
            model.addAttribute("notEmptyCart",true);
        }else{
            model.addAttribute("notEmptyCart",false);
        }
        for (CartItem cartItem : cartItems){
            if(cartItem.getProduct().getProductQuantity() <= 0){
                model.addAttribute("errors",true);
            }
        }
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("user",user);
        model.addAttribute("shoppingCart",shoppingCart);
        return "cart";
    }

    @GetMapping("/removeCartItem")
    public String removeItem(@RequestParam("cartItem") Long cartItemId,Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        List<CartItem> cartItems = cartItemService.cartItems(user.getShoppingCart());
        for(CartItem cartItem : cartItems){
            if(cartItemId.equals(cartItem.getId())){
                cartItemService.removeCartItem(cartItem);
                break;
            }
        }
        return "redirect:/cart";
    }

    @PostMapping("/updateQty")
    public String updateCartItemQty(@ModelAttribute("qty") String qty,
                                    @ModelAttribute("product") String productName,
                                    @ModelAttribute("cartItem") Long cartItemId,
                                    Principal principal){

        User user = userService.getUserByEmail(principal.getName());
        List<CartItem> cartItems = cartItemService.cartItems(user.getShoppingCart());
        for(CartItem cartItem : cartItems){
            if(cartItemId.equals(cartItem.getId())){
                cartItemService.updateCartItemFromCart(cartItem,Integer.parseInt(qty));
                break;
            }
        }
        return "redirect:/cart";
    }
}
