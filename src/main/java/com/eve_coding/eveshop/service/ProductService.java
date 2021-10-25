package com.eve_coding.eveshop.service;

import com.eve_coding.eveshop.model.Product;
import com.eve_coding.eveshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void saveNewProduct(Product product){
        productRepository.save(product);
    }

    public List<Product> products(){
        return productRepository.findAll();
    }

    public Product product(String productName){
        return productRepository.findProductByProductName(productName);
    }

    public List<Product> getLatestProducts(){
        Pageable pageable = PageRequest.of(0,3);
        return productRepository.getLatestProducts(pageable);
    }
    public Page<Product> getActiveProducts(int pageNo){
        Pageable pageable = PageRequest.of(pageNo - 1, 8);
        return productRepository.getAllActiveProducts(pageable);
    }

    public Page<Product> getProductsByCategory(String category,int pageNo){
        Pageable pageable = PageRequest.of(pageNo - 1, 8);
        return productRepository.getProductsByProductCategory(category,pageable);
    }
    public Page<Product> getProductsLike(String productName){
        Pageable pageable = PageRequest.of(0,3);
        return productRepository.findByProductNameLike("%"+productName+"%",pageable);
    }

    public int productsOutOfStock(){
        return productRepository.countProductsByProductQuantity();
    }

    public Page<Product> getProductsOutOfStock(int page){
        Pageable pageable = PageRequest.of(page-1,8);
        return productRepository.getProductsOutOfStock(pageable);
    }
}
