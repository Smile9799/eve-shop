package com.eve_coding.eveshop.controller;

import com.eve_coding.eveshop.model.*;
import com.eve_coding.eveshop.service.CartItemService;
import com.eve_coding.eveshop.service.OrderService;
import com.eve_coding.eveshop.service.ShoppingCartService;
import com.eve_coding.eveshop.service.UserService;
import com.eve_coding.eveshop.utils.Province;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class CheckoutController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        List<CartItem> cartItemList = cartItemService.cartItems(user.getShoppingCart());

        for(CartItem cartItem : cartItemList){
            if(cartItem.getProduct().getProductQuantity() < cartItem.getQty()){
                model.addAttribute("errors",true);
                model.addAttribute("productName",cartItem.getProduct().getProductName());
                model.addAttribute("lowStock",true);
                model.addAttribute("cartItems", cartItemList);
                model.addAttribute("user",user);
                model.addAttribute("notEmptyCart",true);
                model.addAttribute("shoppingCart",user.getShoppingCart());
                return "cart";
            }
        }

        model.addAttribute("cartItemList",cartItemList);
        model.addAttribute("user",user);
        model.addAttribute("shippingAddresses",user.getShippingAddresses());
        model.addAttribute("provinces", Province.provinces);
        model.addAttribute("shippingAddress",new UserShippingAddress());
        model.addAttribute("billingAddress",new OrderBillingAddress());
        return "checkout";
    }

    @GetMapping("/setShippingAddress")
    public String setShippingAddress(@RequestParam("addressId") Long addressId, Principal principal, Model model){

        UserShippingAddress userShippingAddress = userService.userShippingAddress(addressId);
        User user = userService.getUserByEmail(principal.getName());
        List<CartItem> cartItemList = cartItemService.cartItems(user.getShoppingCart());

        if(userShippingAddress.getUser().getUserId().equals(user.getUserId())){
            model.addAttribute("shippingAddress",userShippingAddress);
        }else {
            model.addAttribute("shippingAddress",new UserShippingAddress());
        }

        model.addAttribute("cartItemList",cartItemList);
        model.addAttribute("user",user);
        model.addAttribute("shippingAddresses",user.getShippingAddresses());
        model.addAttribute("provinces", Province.provinces);
        model.addAttribute("billingAddress",new OrderBillingAddress());
        return "checkout";
    }

    @PostMapping("/createOrder")
    public String checkout(@ModelAttribute("billingAddress") OrderBillingAddress orderBillingAddress,
                           @ModelAttribute("shippingAddress") UserShippingAddress shippingAddress,
                           Principal principal, Model model,
                           @ModelAttribute("sameAsShipping") String sameAsShipping){
        System.out.println(shippingAddress.getUserShippingName());
        if(sameAsShipping.equals("true") || sameAsShipping.equals("1")){
            orderBillingAddress.setOrderBillingAddressName(shippingAddress.getUserShippingName());
            orderBillingAddress.setOrderBillingAddressStreet1(shippingAddress.getUserShippingStreet1());
            orderBillingAddress.setOrderBillingAddressStreet2(shippingAddress.getUserShippingStreet2());
            orderBillingAddress.setOrderBillingAddressProvince(shippingAddress.getUserShippingProvince());
            orderBillingAddress.setOrderBillingAddressZipCode(shippingAddress.getUserShippingZipCode());
            orderBillingAddress.setOrderBillingAddressCity(shippingAddress.getUserShippingCity());
        }
        User user = userService.getUserByEmail(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();
        OrderShippingAddress orderShippingAddress = new OrderShippingAddress();
        orderShippingAddress.setOrderShippingAddressCity(shippingAddress.getUserShippingCity());
        orderShippingAddress.setOrderShippingAddressName(shippingAddress.getUserShippingName());
        orderShippingAddress.setOrderShippingAddressStreet1(shippingAddress.getUserShippingStreet1());
        orderShippingAddress.setOrderShippingAddressStreet2(shippingAddress.getUserShippingStreet2());
        orderShippingAddress.setOrderShippingAddressZipCode(shippingAddress.getUserShippingZipCode());
        orderShippingAddress.setOrderShippingAddressProvince(shippingAddress.getUserShippingProvince());

        Long orderId = orderService.createOrder(user,orderBillingAddress,orderShippingAddress);

        shoppingCartService.clearCart(shoppingCart);

        return "redirect:/orderDetail?id="+orderId;
    }
}
