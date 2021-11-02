package com.eve_coding.eveshop.controller;

import com.eve_coding.eveshop.model.Order;
import com.eve_coding.eveshop.model.Product;
import com.eve_coding.eveshop.model.ProductCategory;
import com.eve_coding.eveshop.service.OrderService;
import com.eve_coding.eveshop.service.ProductCategoryService;
import com.eve_coding.eveshop.service.ProductService;
import com.eve_coding.eveshop.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        log.info("starting admin dashboard");
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

        log.info("visiting out stock product page");
        return "admin-view-products";
    }

    @GetMapping("/view/orders")
    public String viewOrders(Model model){
        model.addAttribute("orders",orderService.getAllOrders());
        log.info("visiting all orders page");
        return "admin-view-orders";
    }

    @GetMapping("/order")
    public String viewOrderDetails(Model model, @RequestParam("orderId") Long orderId){
        model.addAttribute("order",orderService.getOrder(orderId));
        log.info("visiting single order page");
        return "admin-order-details";
    }

    @GetMapping("/add/product")
    public String adminAddProduct(Model model){
        List<ProductCategory> productCategoryList = productCategoryService.productCategories();
        model.addAttribute("categoryList",productCategoryList);
        model.addAttribute("product",new Product());

        log.info("visiting add product page");
        return "admin-add-product";
    }

    @PostMapping("/order/status")
    public String updateStatus(@ModelAttribute("status") String status, @ModelAttribute("orderId") Long orderId){
        Order order = orderService.getOrder(orderId);
        if(order != null){
            order.setOrderStatus(status);
            orderService.updateOrder(order);
            log.info("changing order status");
            return "redirect:/admin/order?orderId="+orderId;
        }else {
            log.error("did not find the order with that Id");
            return "bad-request-page";
        }
    }

    @GetMapping("/add/category")
    public String adminAddCategory(Model model){
        model.addAttribute("productCategory",new ProductCategory());
        log.info("visiting add product category page");
        return "admin-add-category";
    }

    @GetMapping("/view/products")
    public String adminViewProducts(Model model){
        model.addAttribute("products",productService.products());
        log.info("visiting all products page");
        return "admin-view-products";
    }
    @GetMapping("/view/categories")
    public String adminViewCategories(Model model){
        model.addAttribute("categories",productCategoryService.productCategories());
        log.info("visiting all product category page");
        return "admin-view-categories";
    }

    @GetMapping("/manage")
    public String adminViewSingleProduct(Model model, @RequestParam("product") String productName){
        model.addAttribute("product",productService.product(productName));

        log.info("visiting product page");
        return "admin-view-single-product";
    }

    @GetMapping("/edit/product")
    public String adminEditProduct(@RequestParam("product")String productName, Model model){

        Product product = productService.product(productName);
        List<ProductCategory> categories = productCategoryService.productCategories();
        model.addAttribute("categoryList",categories);
        model.addAttribute("product",product);
        log.info("visiting edit product page");
        return "admin-edit-product";
    }

    @PostMapping("/adminAddCategory")
    public String adminAddCategory(@ModelAttribute("productCategory") ProductCategory productCategory){
        log.info("add new category");
        try {
            productCategory.setCategoryImageStr(Base64.getEncoder().encodeToString(productCategory.getMultipartFile().getBytes()));
        } catch (IOException e) {
            log.error("error uploading product category image",e.fillInStackTrace());
        }
        productCategoryService.addNewCategory(productCategory);
        return "redirect:/admin/view/products";
    }

    @PostMapping("/adminAddNewProduct")
    public String adminAddNewProduct(@ModelAttribute("product") Product product){
        log.info("add new product");
        try {
            product.setProductImgStr(Base64.getEncoder().encodeToString(product.getMultipartFile().getBytes()));
        } catch (IOException e) {
            log.error("error uploading product image",e.fillInStackTrace());
        }
        productService.saveNewProduct(product);
        return "redirect:/admin/view/products";
    }

    @PostMapping("/adminUpdateProduct")
    public String adminEditProduct(@ModelAttribute("product") Product product){

        log.info("editing product information");
        if(!product.getMultipartFile().isEmpty()){
            try {
                product.setProductImgStr(Base64.getEncoder().encodeToString(product.getMultipartFile().getBytes()));
            } catch (IOException e) {
                log.error("error uploading product image",e.fillInStackTrace());
            }
        }else{
            product.setProductImgStr(product.getProductImgStr());
        }

        productService.saveNewProduct(product);

        return "redirect:/admin/manage?product="+product.getProductName();
    }

}
