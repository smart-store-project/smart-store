package com.codegym.controller;

import com.codegym.model.Cart;
import com.codegym.model.CartItem;
import com.codegym.model.dto.UserDto;
import com.codegym.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("cart")
@RequestMapping("/user/cart")
public class CartController {

    @Autowired
    private IProductService productService;

    @ModelAttribute("cart")
    public Cart setupCart(@SessionAttribute("user") UserDto userDto) {
        if (userDto != null) {
            CartItem cartItem1 = new CartItem(productService.findById(1L), 3);
            CartItem cartItem2 = new CartItem(productService.findById(2L), 2);
            Cart cart = new Cart(userDto.getId());
            cart.getCart().add(cartItem1);
            cart.getCart().add(cartItem2);
            return cart;
        } else {
            return new Cart();
        }
    }
    @ModelAttribute("user")
    private UserDto currentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (UserDto) session.getAttribute("user");
    }

    @GetMapping
    public ModelAndView showCart(@ModelAttribute("cart") Cart cart) {
        ModelAndView modelAndView = new ModelAndView("/user/cart");
        modelAndView.addObject("cart", cart);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteCart(@PathVariable("id") Long id, @SessionAttribute("cart") Cart cart) {
        cart.removeCartItem(id);
        return new ModelAndView("redirect:/cart");
    }

}
