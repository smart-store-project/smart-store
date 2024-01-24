package com.codegym.service.impl;

import com.codegym.model.PaymentMethod;
import com.codegym.repository.IPaymentRepository;
import com.codegym.service.IPaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentMethodService implements IPaymentMethodService {
    @Autowired
    private IPaymentRepository iPaymentRepository;

    @Override
    public Iterable<PaymentMethod> findAll() {
        return iPaymentRepository.findAll();
    }

    @Override
    public Optional<PaymentMethod> findById(Long id) {
        return iPaymentRepository.findById(id);
    }

    @Override
    public void save(PaymentMethod paymentMethod) {
        iPaymentRepository.save(paymentMethod);
    }

    @Override
    public void remove(Long id) {
        iPaymentRepository.deleteById(id);
    }
}
