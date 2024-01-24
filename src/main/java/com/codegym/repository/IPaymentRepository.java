package com.codegym.repository;

import com.codegym.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPaymentRepository extends JpaRepository<PaymentMethod, Long> {
}
