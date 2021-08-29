package com.eve_coding.eveshop.repository;

import com.eve_coding.eveshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Product findProductByProductName(String productName);
}
