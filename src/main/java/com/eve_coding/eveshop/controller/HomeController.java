package com.eve_coding.eveshop.controller;

import com.eve_coding.eveshop.model.Product;
import com.eve_coding.eveshop.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SecurityService securityService;


    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private OrderService orderService;


    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("latestProducts",productService.getLatestProducts());
        log.info("starting home page");
        return "home";
    }

    @GetMapping("/search")
    public String search(Model model, @ModelAttribute("search") String name, @RequestParam("page") Optional<Integer> page){

        int pageNo = page.orElse(1); // get the page number if not specified set to 1
        Page<Product> products = productService.searchProduct(name,pageNo);

        model.addAttribute("categoryList",productCategoryService.productCategories());
        model.addAttribute("productsList",products.getContent());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",products.getTotalPages());
        model.addAttribute("totalContent",products.getTotalElements());

        log.info("starting search results page");
        return "search-result-page";
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

        log.info("starting shop page");
        return "shop";
    }

    @GetMapping("/categories")
    public String shopCategories(Model model){
        model.addAttribute("categoryList",productCategoryService.productCategories());

        log.info("starting categories page");
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

        log.info("starting category shop page");
        return "shop";
    }
    @GetMapping("/product")
    public String product(@RequestParam("product") String productName, Model model){
        Page<Product> page = productService.getProductsLike(productName);

        model.addAttribute("products",page.getContent());
        model.addAttribute("product",productService.product(productName));

        log.info("starting shop item page");
        return "shop-item";
    }
}
