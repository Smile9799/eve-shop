package com.eve_coding.eveshop.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
@Table(name = "tbl_users")
@Getter
@Setter
public class User {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "user_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    @Transient
    private String passwordConfirm;

    private String password;

    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<Role> roles;

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
