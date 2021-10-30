package com.eve_coding.eveshop.controller;

import com.eve_coding.eveshop.model.Order;
import com.eve_coding.eveshop.model.Product;
import com.eve_coding.eveshop.model.ProductCategory;
import com.eve_coding.eveshop.service.OrderService;
import com.eve_coding.eveshop.service.ProductCategoryService;
import com.eve_coding.eveshop.service.ProductService;
import com.eve_coding.eveshop.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@RequestMapping("/admin")
@Controller
public class AdminController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model){
        model.addAttribute("outOfStock",productService.productsOutOfStock());
        model.addAttribute("numberOfOrders",orderService.getNumberOfOrders());

        model.addAttribute("products",productService.getProductsOutOfStock(1));
        model.addAttribute("orders",orderService.getLatestOrders());
        model.addAttribute("cancelledOrders",orderService.getNumberOfOrdersByStatus("cancelled"));
        model.addAttribute("completedOrders",orderService.getNumberOfOrdersByStatus("successful"));
        return "dashboard";
    }

    @GetMapping("/outOfStock/{page}")
    public String outOfStock(Model model, @PathVariable("page") Optional<Integer> page){
        int pageNo = page.orElse(1);
        List<Product>  products = productService.getProductsOutOfStock(pageNo).getContent();
        model.addAttribute("products",products);
        return "admin-view-products";
    }

    @GetMapping("/view/orders")
    public String viewOrders(Model model){
        model.addAttribute("orders",orderService.getAllOrders());
        return "admin-view-orders";
    }

    @GetMapping("/order")
    public String viewOrderDetails(Model model, @RequestParam("orderId") Long orderId){
        model.addAttribute("order",orderService.getOrder(orderId));
        return "admin-order-details";
    }

    @GetMapping("/add/product")
    public String adminAddProduct(Model model){
        List<ProductCategory> productCategoryList = productCategoryService.productCategories();
        model.addAttribute("categoryList",productCategoryList);
        model.addAttribute("product",new Product());
        return "admin-add-product";
    }

    @PostMapping("/order/status")
    public String updateStatus(@ModelAttribute("status") String status, @ModelAttribute("orderId") Long orderId){
        Order order = orderService.getOrder(orderId);
        if(order != null){
            order.setOrderStatus(status);
            orderService.updateOrder(order);
            return "redirect:/admin/order?orderId="+orderId;
        }else {
            return "bad-request-page";
        }
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
        try {
            productCategory.setCategoryImageStr(Base64.getEncoder().encodeToString(productCategory.getMultipartFile().getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
