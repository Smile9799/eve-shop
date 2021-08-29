package com.eve_coding.eveshop.service;

import com.eve_coding.eveshop.model.Product;
import com.eve_coding.eveshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
