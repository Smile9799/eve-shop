package com.eve_coding.eveshop.repository;

import com.eve_coding.eveshop.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {
}
