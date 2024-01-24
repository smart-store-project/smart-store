package com.codegym.utils;

import com.codegym.model.Cart;
import com.codegym.model.CartItem;
import com.codegym.model.Order;
import com.codegym.model.OrderDetail;
import com.codegym.model.OrderStatus;
import com.codegym.model.PaymentMethod;
import com.codegym.model.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class ConverterToOrder {

    public static Order convert(Cart cart, User user, PaymentMethod paymentMethod, OrderStatus orderStatus) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(orderStatus);
        order.setTotalPrice(cart.getTotalPrice().floatValue());
        order.setPaymentMethod(paymentMethod);

        Set<OrderDetail> orderDetails = new HashSet<>();

        for (CartItem cartItem : cart.getCart()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setUnitPrice(cartItem.getProduct().getPrice().floatValue());
            orderDetail.setProduct(cartItem.getProduct());
            order.addOrderDetail(orderDetail);

            orderDetails.add(orderDetail);
        }

        order.setOrderDetails(orderDetails);
        return order;
    }
}
