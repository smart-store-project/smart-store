package com.codegym.controller;

import com.codegym.model.Order;
import com.codegym.model.OrderStatus;
import com.codegym.model.dto.UserDto;
import com.codegym.service.IOrderService;
import com.codegym.service.IOrderStatusService;
import com.codegym.service.IUserService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/user/orders")
public class OrderController {
    @Autowired
    private IUserService2 userService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderStatusService orderStatusService;

    @ModelAttribute("user")
    public UserDto getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (UserDto) session.getAttribute("user");
    }

    @GetMapping()
    public ModelAndView showPurchase(@RequestParam(value = "type", defaultValue = "1") Long type,
                                     @ModelAttribute("user") UserDto userDto) {
        OrderStatus orderStatus = orderStatusService.findById(type).orElse(null);
        Iterable<OrderStatus> orderStatuses = orderStatusService.findAll();
        Iterable<Order> orders = orderService.findAllByUserAndOrderStatus(userService.convertUserDtoToUser(userDto).get(), orderStatus);
        ModelAndView modelAndView = new ModelAndView("/user/order/list");
        modelAndView.addObject("orders", orders);
        modelAndView.addObject("orderStatuses", orderStatuses);

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView showOrderView(@PathVariable("id") Long id) {
        Optional<Order> order = orderService.findById(id);
        ModelAndView modelAndView;
        if (order.isPresent()) {
            modelAndView = new ModelAndView("/user/order/view");
            modelAndView.addObject("order", order.get());
        } else {
            modelAndView = new ModelAndView("redirect:/user/order");
            modelAndView.addObject("error", "Order not found");
        }
        return modelAndView;
    }
}
