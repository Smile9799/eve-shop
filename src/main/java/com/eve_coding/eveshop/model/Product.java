package com.eve_coding.eveshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_products")
public class Product {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    private Long productId;

    private String productName;

    @Lob
    private String productDescription;

    @Lob
    private String productImgStr;

    private BigDecimal productPrice;

    private BigDecimal productDiscountedPrice;

    private boolean isOnSale;

    private boolean isActive;

    //private String productSku;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedOn;

    private int productQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory productCategory;

    @Transient
    public MultipartFile multipartFile;
}
