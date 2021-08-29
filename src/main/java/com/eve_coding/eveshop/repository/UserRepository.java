package com.eve_coding.eveshop.repository;

import com.eve_coding.eveshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User getUserByEmail(String email);
}
