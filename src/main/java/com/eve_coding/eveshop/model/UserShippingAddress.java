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
public class UserShippingAddress {

    @Id
    @SequenceGenerator(
            name = "user_shipping_address_sequence",
            sequenceName = "user_shipping_address_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "user_shipping_address_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long userShippingAddressId;
    private String userShippingName;
    private String userShippingStreet1;
    private String userShippingStreet2;
    private String userShippingCity;
    private String userShippingProvince;
    private String userShippingCountry;
    private String userShippingZipCode;
    private boolean userShippingDefault;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
