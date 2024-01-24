package com.codegym.repository;

import com.codegym.model.Order;
import com.codegym.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    Iterable<OrderDetail> findByOrder(Order order);

}
