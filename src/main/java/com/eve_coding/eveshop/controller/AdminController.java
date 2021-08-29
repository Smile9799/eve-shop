package com.eve_coding.eveshop.controller;

import com.eve_coding.eveshop.dto.ProductDto;
import com.eve_coding.eveshop.model.Product;
import com.eve_coding.eveshop.model.ProductCategory;
import com.eve_coding.eveshop.service.ProductCategoryService;
import com.eve_coding.eveshop.service.ProductService;
import com.eve_coding.eveshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String adminDashboard(){
        return "admin/dashboard";
    }

    @GetMapping("/addProduct")
    public String adminAddProduct(Model model){
        List<ProductCategory> productCategoryList = productCategoryService.productCategories();
        model.addAttribute("categoryList",productCategoryList);
        model.addAttribute("product",new Product());
        return "admin/admin-add-product";
    }

    @GetMapping("/addCategory")
    public String adminAddCategory(Model model){
        model.addAttribute("productCategory",new ProductCategory());
        return "admin/admin-add-category";
    }

    @GetMapping("/viewProducts")
    public String adminViewProducts(Model model){
        model.addAttribute("products",productService.products());
        return "admin/admin-view-products";
    }

    @GetMapping("/manage")
    public String adminViewSingleProduct(Model model, @RequestParam("product") String productName){
        model.addAttribute("product",productService.product(productName));
        return "admin/admin-view-single-product";
    }


    @PostMapping("/adminAddCategory")
    public String adminAddCategory(@ModelAttribute("productCategory") ProductCategory productCategory){
        productCategoryService.addNewCategory(productCategory);
        return "redirect:/addProduct";
    }

    @PostMapping("/adminAddNewProduct")
    public String adminAddNewProduct(@ModelAttribute("product") Product product){
        productService.saveNewProduct(product);
        return "redirect:/addProduct";
    }
}
