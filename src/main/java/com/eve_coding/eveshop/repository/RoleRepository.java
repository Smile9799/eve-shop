package com.eve_coding.eveshop.repository;

import com.eve_coding.eveshop.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Role getRoleByRoleName(String roleName);
}
