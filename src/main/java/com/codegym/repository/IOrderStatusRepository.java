package com.codegym.repository;

import com.codegym.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderStatusRepository extends JpaRepository<OrderStatus, Long> {
}
