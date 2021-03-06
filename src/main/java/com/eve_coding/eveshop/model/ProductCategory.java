package com.eve_coding.eveshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_product_category")
public class ProductCategory {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    private Long categoryId;

    private String categoryName;

    @Lob
    private String categoryImageStr;

    private String categoryDescription;

    @OneToMany(
            mappedBy = "productCategory",
            cascade = CascadeType.ALL
    )
    private Set<Product> products;

    @Transient
    private MultipartFile multipartFile;
}
