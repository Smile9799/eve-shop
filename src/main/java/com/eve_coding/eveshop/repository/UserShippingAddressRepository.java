package com.eve_coding.eveshop.repository;

import com.eve_coding.eveshop.model.User;
import com.eve_coding.eveshop.model.UserShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserShippingAddressRepository extends JpaRepository<UserShippingAddress,Long> {

}
