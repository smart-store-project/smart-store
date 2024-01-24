package com.codegym.repository;

import com.codegym.model.Order;
import com.codegym.model.OrderStatus;
import com.codegym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, Long> {
    Iterable<Order> findAllByUser(User user);
    Iterable<Order> findByUserAndOrderStatus(User user, OrderStatus orderStatus);
}
