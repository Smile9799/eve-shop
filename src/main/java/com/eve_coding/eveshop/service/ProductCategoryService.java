package com.eve_coding.eveshop.service;

import com.eve_coding.eveshop.model.ProductCategory;
import com.eve_coding.eveshop.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public void addNewCategory(ProductCategory productCategory){
        productCategoryRepository.save(productCategory);
    }

    public List<ProductCategory> productCategories(){
        return productCategoryRepository.findAll();
    }
}
