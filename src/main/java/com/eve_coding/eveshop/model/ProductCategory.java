package com.eve_coding.eveshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @SequenceGenerator(
            name = "sequence_category",
            sequenceName = "sequence_category",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_category"
    )
    private Long categoryId;

    private String categoryName;

    @Column(columnDefinition = "text")
    private String categoryImageStr;

    private String categoryDescription;

    @OneToMany(
            mappedBy = "productCategory",
            cascade = CascadeType.ALL
    )
    private Set<Product> products;
}
