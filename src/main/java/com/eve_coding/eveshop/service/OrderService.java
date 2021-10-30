package com.eve_coding.eveshop.service;

import com.eve_coding.eveshop.model.*;
import com.eve_coding.eveshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getUserOrders(User user){
        return orderRepository.getOrdersByUser(user);
    }
    public Order getOrder(Long id){
        return orderRepository.getById(id);
    }

    public synchronized Order createOrder(User user, OrderBillingAddress orderBillingAddress, OrderShippingAddress shippingAddress){
        ShoppingCart shoppingCart = user.getShoppingCart();
        List<CartItem> cartItemList = shoppingCart.getCartItemList();
        BigDecimal additionalFees = new BigDecimal(String.valueOf((shoppingCart.getGrandTotal().multiply(new BigDecimal("0.15"))).add(new BigDecimal(100)))).setScale(2, RoundingMode.HALF_UP);

        Order order = new Order();
        order.setOrderStatus("created");
        order.setOrderShippingAddress(shippingAddress);
        order.setOrderBillingAddress(orderBillingAddress);

        for(CartItem cartItem : cartItemList){
            cartItem.setOrder(order);
            cartItem.getProduct().setProductQuantity(cartItem.getProduct().getProductQuantity() - cartItem.getQty());
        }
        order.setOrderDate(Calendar.getInstance().getTime());
        order.setOrderTotal(shoppingCart.getGrandTotal().add(additionalFees));
        order.setUser(user);

        shippingAddress.setOrder(order);
        orderBillingAddress.setOrder(order);
        order = orderRepository.save(order);
        return order;
    }

    public int getNumberOfOrders(){
        return orderRepository.countAllOrders();
    }
    public int getNumberOfOrdersByStatus(String orderStatus){
        return orderRepository.countOrderByOrderStatus(orderStatus);
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }
    public void updateOrder(Order order){
        orderRepository.save(order);
    }
    public List<Order> getLatestOrders(){
        return orderRepository.getOrdersByOrderStatusOrderByOrderDateDesc("created");
    }
}
