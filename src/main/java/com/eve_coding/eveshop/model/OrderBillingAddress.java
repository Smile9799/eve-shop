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
public class OrderBillingAddress {

    @Id
    @SequenceGenerator(
            name = "order_billing_address_sequence",
            sequenceName = "order_billing_address_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "order_billing_address_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long orderBillingAddressId;
    private String orderBillingAddressName;
    private String orderBillingAddressStreet1;
    private String orderBillingAddressStreet2;
    private String orderBillingAddressCity;
    private String orderBillingAddressProvince;
    private String orderBillingAddressCountry;
    private String orderBillingAddressZipCode;

    @OneToOne
    private Order order;
}
