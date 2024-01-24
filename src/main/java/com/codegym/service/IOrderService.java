package com.codegym.service;

import com.codegym.exception.UnavailableBalanceException;
import com.codegym.model.Cart;
import com.codegym.model.Order;
import com.codegym.model.OrderStatus;
import com.codegym.model.User;
import com.codegym.model.dto.UserDto;

public interface IOrderService extends IGenerateService<Order>{
    void addOrder(Order order, Cart cart) throws UnavailableBalanceException;
    void addOrder(Order order, UserDto userDto) throws UnavailableBalanceException;
    Iterable<Order> findAllByUserAndOrderStatus(User user, OrderStatus orderStatus);
    Iterable<Order> findAllByUserAndOrderStatus_v2(UserDto userDto, OrderStatus orderStatus);
}
