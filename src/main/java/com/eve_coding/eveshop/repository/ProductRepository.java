package com.eve_coding.eveshop.repository;

import com.eve_coding.eveshop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Product findProductByProductName(String productName);
    @Query("select p from Product p order by p.createdAt desc ")
    List<Product> getLatestProducts(Pageable pageable);

    @Query("select p from Product p where p.isActive=true ")
    Page<Product> getAllActiveProducts(Pageable pageable);

    @Query("select p from Product p where p.productCategory.categoryName=?1 and p.isActive = true ")
    Page<Product> getProductsByProductCategory(String category,Pageable pageable);

    Page<Product> findByProductNameLike(String productName, Pageable pageable);

    @Query("select count (p) from Product p where p.productQuantity = 0")
    int countProductsByProductQuantity();

    @Query("select p from Product p where p.productQuantity = 0")
    Page<Product> getProductsOutOfStock(Pageable pageable);
}
