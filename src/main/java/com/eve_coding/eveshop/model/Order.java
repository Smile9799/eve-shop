package com.eve_coding.eveshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_orders")
public class Order {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    public Long orderId;

    public Date orderDate;

    public Date shippingDate;

    public String shippingMethod;

    public String orderStatus;

    public BigDecimal orderTotal;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    @OneToOne(cascade = CascadeType.ALL)
    private OrderShippingAddress orderShippingAddress;

    @OneToOne(cascade = CascadeType.ALL)
    private OrderBillingAddress orderBillingAddress;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
}
