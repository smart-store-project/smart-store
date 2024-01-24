package com.codegym.service.impl;

import com.codegym.model.Cart;
import com.codegym.model.Order;
import com.codegym.model.OrderStatus;
import com.codegym.repository.IOrderStatusRepository;
import com.codegym.service.IOrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderStatusService implements IOrderStatusService {
    @Autowired
    private IOrderStatusRepository iOrderStatusRepository;

    @Override
    public Iterable<OrderStatus> findAll() {
        return iOrderStatusRepository.findAll();
    }

    @Override
    public Optional<OrderStatus> findById(Long id) {
        return iOrderStatusRepository.findById(id);
    }

    @Override
    public void save(OrderStatus orderStatus) {
        iOrderStatusRepository.save(orderStatus);
    }

    @Override
    public void remove(Long id) {
        iOrderStatusRepository.deleteById(id);
    }
}
