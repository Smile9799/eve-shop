package com.eve_coding.eveshop.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tbl_roles")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    private Long roleId;
    private String roleName;
    private String roleDescription;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role(String roleName, String roleDescription){
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }

    public Role() {

    }
}
