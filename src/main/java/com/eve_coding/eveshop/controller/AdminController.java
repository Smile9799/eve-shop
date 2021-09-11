package com.eve_coding.eveshop.controller;

import com.eve_coding.eveshop.model.Product;
import com.eve_coding.eveshop.model.ProductCategory;
import com.eve_coding.eveshop.service.ProductCategoryService;
import com.eve_coding.eveshop.service.ProductService;
import com.eve_coding.eveshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;
import java.util.List;


@RequestMapping("/admin")
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
        return "dashboard";
    }

    @GetMapping("/add/product")
    public String adminAddProduct(Model model){
        List<ProductCategory> productCategoryList = productCategoryService.productCategories();
        model.addAttribute("categoryList",productCategoryList);
        model.addAttribute("product",new Product());
        return "admin-add-product";
    }

    @GetMapping("/add/category")
    public String adminAddCategory(Model model){
        model.addAttribute("productCategory",new ProductCategory());
        return "admin-add-category";
    }

    @GetMapping("/view/products")
    public String adminViewProducts(Model model){
        model.addAttribute("products",productService.products());
        return "admin-view-products";
    }
    @GetMapping("/view/categories")
    public String adminViewCategories(Model model){
        model.addAttribute("categories",productCategoryService.productCategories());
        return "admin-view-categories";
    }

    @GetMapping("/manage")
    public String adminViewSingleProduct(Model model, @RequestParam("product") String productName){
        model.addAttribute("product",productService.product(productName));
        return "admin-view-single-product";
    }

    @GetMapping("/edit/product")
    public String adminEditProduct(@RequestParam("product")String productName, Model model){

        Product product = productService.product(productName);
        List<ProductCategory> categories = productCategoryService.productCategories();
        model.addAttribute("categoryList",categories);
        model.addAttribute("product",product);
        return "admin-edit-product";
    }

    @PostMapping("/adminAddCategory")
    public String adminAddCategory(@ModelAttribute("productCategory") ProductCategory productCategory){
        productCategoryService.addNewCategory(productCategory);
        return "redirect:/admin/view/products";
    }

    @PostMapping("/adminAddNewProduct")
    public String adminAddNewProduct(@ModelAttribute("product") Product product){
        try {
            product.setProductImgStr(Base64.getEncoder().encodeToString(product.getMultipartFile().getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        productService.saveNewProduct(product);
        return "redirect:/admin/view/products";
    }

    @PostMapping("/adminUpdateProduct")
    public String adminEditProduct(@ModelAttribute("product") Product product){

        if(!product.getMultipartFile().isEmpty()){
            try {
                product.setProductImgStr(Base64.getEncoder().encodeToString(product.getMultipartFile().getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            product.setProductImgStr(product.getProductImgStr());
        }

        productService.saveNewProduct(product);

        return "redirect:/admin/manage?product="+product.getProductName();
    }

}
