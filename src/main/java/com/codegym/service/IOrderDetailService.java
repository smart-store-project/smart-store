package com.codegym.service;


import com.codegym.model.Cart;
import com.codegym.model.OrderDetail;
import com.codegym.model.OrderStatus;
import com.codegym.model.User;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;


public interface IOrderDetailService{
    Iterable<OrderDetail> findAllByUser(User user);
    boolean add(OrderDetail orderDetail, @SessionAttribute("cart") Cart cart);
    boolean addAll(Iterable<OrderDetail> orderDetails,  @SessionAttribute("cart") Cart cart);

}
