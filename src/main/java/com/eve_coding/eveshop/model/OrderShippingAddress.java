package com.eve_coding.eveshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderShippingAddress {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    private Long orderShippingAddressId;
    private String orderShippingAddressName;
    private String orderShippingAddressStreet1;
    private String orderShippingAddressStreet2;
    private String orderShippingAddressCity;
    private String orderShippingAddressProvince;
    private String orderShippingAddressCountry;
    private String orderShippingAddressZipCode;

    @OneToOne
    private Order order;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public OrderShippingAddress(String orderShippingAddressName, String orderShippingAddressStreet1, String orderShippingAddressStreet2, String orderShippingAddressCity, String orderShippingAddressProvince, String orderShippingAddressZipCode) {
        this.orderShippingAddressName = orderShippingAddressName;
        this.orderShippingAddressStreet1 = orderShippingAddressStreet1;
        this.orderShippingAddressStreet2 = orderShippingAddressStreet2;
        this.orderShippingAddressCity = orderShippingAddressCity;
        this.orderShippingAddressProvince = orderShippingAddressProvince;
        this.orderShippingAddressZipCode = orderShippingAddressZipCode;
    }
}
