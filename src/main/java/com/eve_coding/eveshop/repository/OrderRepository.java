package com.eve_coding.eveshop.repository;

import com.eve_coding.eveshop.model.Order;
import com.eve_coding.eveshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> getOrdersByUser(User user);
}
