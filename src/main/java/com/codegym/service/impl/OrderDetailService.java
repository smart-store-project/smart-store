package com.codegym.service.impl;

import com.codegym.model.Cart;
import com.codegym.model.Order;
import com.codegym.model.OrderDetail;
import com.codegym.model.User;
import com.codegym.repository.IOrderDetailRepository;
import com.codegym.repository.IOrderRepository;
import com.codegym.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;


@Service
public class OrderDetailService implements IOrderDetailService {
    @Autowired
    private IOrderDetailRepository iOrderDetailRepository;

    @Autowired
    private IOrderRepository iOrderRepository;

    @Override
    public Iterable<OrderDetail> findAllByUser(User user) {
        Iterable<Order> orders = iOrderRepository.findAllByUser(user);
        List<OrderDetail> orderDetails = new ArrayList<>();

        for (Order order : orders) {
            Iterable<OrderDetail> orderDetails1 = iOrderDetailRepository.findByOrder(order);
          orderDetails1.forEach(orderDetails::add);
        }
        return orderDetails;
    }

    @Override
    public boolean add(OrderDetail orderDetail, @SessionAttribute("cart") Cart cart) {
        try {
            iOrderDetailRepository.save(orderDetail);
            cart.removeCartItem(orderDetail.getProduct().getId());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean addAll(Iterable<OrderDetail> orderDetails,  @SessionAttribute("cart") Cart cart) {
        boolean canAdd = true;
        for (OrderDetail orderDetail : orderDetails) {
            canAdd = add(orderDetail,cart);
            if (!canAdd) {
                return false;
            }
        }
        return canAdd;
    }
}
