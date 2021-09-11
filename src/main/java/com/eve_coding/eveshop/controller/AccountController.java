package com.eve_coding.eveshop.controller;

import com.eve_coding.eveshop.model.Order;
import com.eve_coding.eveshop.model.User;
import com.eve_coding.eveshop.model.UserShippingAddress;
import com.eve_coding.eveshop.service.*;
import com.eve_coding.eveshop.utils.Province;
import com.eve_coding.eveshop.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @GetMapping("/registration")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }

        model.addAttribute("user", new User());

        return "registration";
    }

    @GetMapping("/account")
    public String account(Model model, Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user",user);
        model.addAttribute("classActiveEdit",true);
        return "profile";
    }

    @GetMapping("/orders")
    public String orders(Model model,Principal principal){
        model.addAttribute("orderList",orderService.getUserOrders(userService.getUserByEmail(principal.getName())));
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        model.addAttribute("classActiveOrders",true);
        return "profile";
    }

    @GetMapping("/orderDetail")
    public String orderDetails(@RequestParam("id") Long orderId, Model model,Principal principal){
        model.addAttribute("order",orderService.getOrder(orderId));
        model.addAttribute("orderList",orderService.getUserOrders(userService.getUserByEmail(principal.getName())));
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        model.addAttribute("displayOrderDetail",true);
        model.addAttribute("classActiveOrders",true);
        return "profile";
    }

    @GetMapping("/addresses")
    public String shippingAddress(Model model){
        model.addAttribute("classActiveShipping",true);
        model.addAttribute("userShipping",new UserShippingAddress());
        return "profile";
    }

    @GetMapping("/addShippingAddress")
    public String addShippingAddress(Model model, Principal principal){

        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user",user);
        model.addAttribute("userShippingAddress",new UserShippingAddress());
        model.addAttribute("stateList", Province.provinces);
        model.addAttribute("classActiveShipping",true);
        model.addAttribute("addNewShippingAddress",true);
        return "profile";
    }

    @GetMapping("/shippingAddresses")
    public String listAddress(Model model, Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("classActiveShipping",true);
        model.addAttribute("listOfShippingAddresses",true);
        model.addAttribute("userShippingList",user.getShippingAddresses());
        model.addAttribute("user",user);
        return "profile";
    }

    @GetMapping("/removeShippingAddress")
    public String removeShippingAddress(Principal principal, @RequestParam("id") Long shippingAddressId){
        User user = userService.getUserByEmail(principal.getName());
        UserShippingAddress shippingAddress = userService.userShippingAddress(shippingAddressId);
        if(shippingAddress.getUser().getUserId().equals(user.getUserId())){
            userService.deleteUserShippingAddress(shippingAddress);
            return "redirect:/shippingAddresses";
        }else {
            return "bad-request-page";
        }
    }

    @GetMapping("/updateShippingAddress")
    public String updateShippingAddress(Model model, Principal principal, @RequestParam("id") Long shippingAddressId){
        User user = userService.getUserByEmail(principal.getName());
        UserShippingAddress shippingAddress = userService.userShippingAddress(shippingAddressId);
        if(user.getUserId().equals(shippingAddress.getUser().getUserId())){
            model.addAttribute("user",user);
            model.addAttribute("userShippingAddress",shippingAddress);
            model.addAttribute("stateList", Province.provinces);
            model.addAttribute("classActiveShipping",true);
            model.addAttribute("addNewShippingAddress",true);
            return "profile";
        }else{
            return "bad-request-page";
        }
    }

    @PostMapping("/addShippingAddress")
    public String addNewShippingAddress(@ModelAttribute("userShippingAddress") UserShippingAddress shippingAddress,Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        userService.saveUserShippingAddress(shippingAddress,user);
        return "redirect:/shippingAddresses";
    }

    @PostMapping("/setDefaultShippingAddress")
    public String setDefaultShippingAddress(@ModelAttribute("defaultShippingAddressId") Long userShippingAddressId, Principal principal){

        User user = userService.getUserByEmail(principal.getName());
        UserShippingAddress userShippingAddress = userService.userShippingAddress(userShippingAddressId);
        if(user.getUserId().equals(userShippingAddress.getUser().getUserId())){
            userShippingAddress.setUserShippingDefault(true);
            userService.saveUserShippingAddress(userShippingAddress,user);
            return "redirect:/shippingAddresses";
        }else{
            return "bad-request-page";
        }
    }

    @PostMapping("/updateUserInfo")
    public String updateUserInfo(Principal principal, @ModelAttribute("user") User user,
                                 @ModelAttribute("newPassword") Optional<String> newPassword,
                                 @ModelAttribute("password") Optional<String> password,
                                 Model model){
        User dbUser = userService.getUserByEmail(principal.getName());
        if(!user.getUserId().equals(dbUser.getUserId()) || !user.getEmail().equals(dbUser.getEmail())){
            return "bad-request-page";
        }else{
            String dbPassword = dbUser.getPassword();
            String currentPassword = passwordEncoder.encode(password.orElse(""));
            if(!dbPassword.equals(currentPassword)){
                model.addAttribute("incorrectPassword",true);
                model.addAttribute("user",dbUser);
                model.addAttribute("classActiveEdit",true);
                return "profile";
            }

            dbUser.setPassword(passwordEncoder.encode(newPassword.orElse("")));
            dbUser.setFirstName(user.getFirstName());
            dbUser.setLastName(user.getLastName());
            userService.saveUser(user);
            model.addAttribute("user",user);
            model.addAttribute("classActiveEdit",true);
            model.addAttribute("updateSuccess",true);
            return "profile";
        }
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") User userForm, BindingResult bindingResult) {

        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.saveUser(userForm);

        securityService.autoLogin(userForm.getEmail(), userForm.getPasswordConfirm());

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        if(securityService.isAuthenticated()){
            return "redirect:/";
        }
        return "login";
    }
}
