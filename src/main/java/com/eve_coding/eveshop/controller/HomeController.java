package com.eve_coding.eveshop.controller;

import com.eve_coding.eveshop.model.Product;
import com.eve_coding.eveshop.model.User;
import com.eve_coding.eveshop.model.UserShippingAddress;
import com.eve_coding.eveshop.service.*;
import com.eve_coding.eveshop.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

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


    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("latestProducts",productService.getLatestProducts());
        return "home";
    }

    @GetMapping("/shop")
    public String shopProducts(Model model,@RequestParam("page") Optional<Integer> page){
        int pageNo = page.orElse(1); // get the page number if not specified set to 1
        Page<Product> products = productService.getActiveProducts(pageNo);

        model.addAttribute("categoryList",productCategoryService.productCategories());
        model.addAttribute("productsList",products.getContent());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",products.getTotalPages());
        model.addAttribute("totalContent",products.getTotalElements());
        return "shop";
    }

    @GetMapping("/categories")
    public String shopCategories(Model model){
        model.addAttribute("categoryList",productCategoryService.productCategories());
        return "categories";
    }

    @GetMapping("/category")
    public String shopCategory(Model model,@RequestParam("category") String category,@RequestParam("page") Optional<Integer> page){
        int pageNo = page.orElse(1);
        Page<Product> products = productService.getProductsByCategory(category,pageNo);

        model.addAttribute("productsList",products.getContent());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",products.getTotalPages());
        model.addAttribute("totalContent",products.getTotalElements());
        return "shop";
    }
    @GetMapping("/product")
    public String product(@RequestParam("product") String productName, Model model){
        Page<Product> page = productService.getProductsLike(productName);

        model.addAttribute("products",page.getContent());
        model.addAttribute("product",productService.product(productName));
        return "shop-item";
    }
}
