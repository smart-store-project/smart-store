package com.codegym.controller;


import com.codegym.exception.UnavailableBalanceException;
import com.codegym.model.Cart;
import com.codegym.model.Order;
import com.codegym.model.PaymentMethod;
import com.codegym.model.dto.UserDto;
import com.codegym.service.IOrderService;
import com.codegym.service.IOrderStatusService;
import com.codegym.service.IPaymentMethodService;
import com.codegym.service.IUserService2;
import com.codegym.utils.ConverterToOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



@Controller
@RequestMapping("/user/checkout")
@SessionAttributes("cartPay")
public class CheckoutController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IUserService2 userService2;
    @Autowired
    private IPaymentMethodService paymentMethodService;
    @Autowired
    private IOrderStatusService orderStatusService;


    @ModelAttribute("user")
    private UserDto currentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (UserDto) session.getAttribute("user");
    }

    @GetMapping
    public ModelAndView checkout(@SessionAttribute("cartPay") Cart cartPay,
                                 @ModelAttribute("user") UserDto userDto) {
        Iterable<PaymentMethod> paymentMethods = paymentMethodService.findAll();
        ModelAndView modelAndView = new ModelAndView("/user/checkout");
        modelAndView.addObject("user", userDto);
        modelAndView.addObject("paymentMethods", paymentMethods);
        modelAndView.addObject("selectedPayment", new PaymentMethod());

        modelAndView.addObject("cartPay", cartPay);
        return modelAndView;
    }


    @PostMapping
    public ModelAndView placeOrder(@SessionAttribute("cartPay") Cart cartPay,
                                   @SessionAttribute("cart") Cart cart,
                                   @ModelAttribute("user") UserDto user,
                                   @ModelAttribute("selectedPayment") PaymentMethod paymentMethod,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasFieldErrors()) {
            return new ModelAndView("redirect:/user/checkout");
        }
        PaymentMethod paymentMethod1 = paymentMethodService.findById(paymentMethod.getId()).orElse(null);
        Order order = ConverterToOrder.convert(cartPay, userService2.convertUserDtoToUser(user).get(), paymentMethod1, orderStatusService.findById(2L).get());
        cartPay.getCart().clear();
        cart.removeCartItems(cart.getCart());
        try {
            orderService.addOrder(order, user);
            return new ModelAndView("redirect:/user/orders");
        } catch (UnavailableBalanceException e) {
            ModelAndView modelAndView = new ModelAndView("redirect:/user/checkout");
            redirectAttributes.addFlashAttribute("error", "Balance is unvailable. Please refill wallet");
            return modelAndView;
        }
    }




}
