package com.codegym.api;


import com.codegym.exception.UnavailableBalanceException;
import com.codegym.model.Cart;
import com.codegym.model.Order;
import com.codegym.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;


@RestController
@RequestMapping("/api/orders")
public class OrderAPIController {

    @Autowired
    private IOrderService orderService;

    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody Order order, @SessionAttribute("cartPay") Cart cart) {
        try {
            orderService.addOrder(order, cart);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

    }

}
