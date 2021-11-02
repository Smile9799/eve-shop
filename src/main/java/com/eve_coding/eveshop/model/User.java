package com.eve_coding.eveshop.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tbl_users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    private Long userId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank @Email
    private String email;

    @Transient
    private String passwordConfirm;

    @NotBlank
    private String password;

    private boolean isEnabled;

    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<Role> roles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
    private ShoppingCart shoppingCart;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<UserShippingAddress> shippingAddresses;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Token> tokens = new HashSet<>();

    public User() {

    }

    public User(String name, String surname, String email, String password, Set<Role> roles){
        this.firstName = name;
        this.lastName = surname;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
