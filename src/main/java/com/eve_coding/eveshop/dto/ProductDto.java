package com.eve_coding.eveshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {

    private String productName;

    private String productDescription;

    private String productImgStr;

    private BigDecimal productPrice;

    private BigDecimal productDiscountedPrice;

    private boolean onSale;

    private boolean active;

    private int productQuantity;

    private Long categoryId;

    private MultipartFile imageFIle;

}
