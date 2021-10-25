package com.eve_coding.eveshop.repository;

import com.eve_coding.eveshop.model.Order;
import com.eve_coding.eveshop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> getOrdersByUser(User user);

    Page<Order> getOrdersByOrderStatus(String orderStatus, Pageable pageable);

    int countOrderByOrderStatus(String orderStatus);

    @Query("select count (o) from Order o")
    int countAllOrders();

}
