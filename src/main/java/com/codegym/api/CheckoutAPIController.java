package com.codegym.api;

import com.codegym.model.Cart;
import com.codegym.model.CartItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/checkout")
@SessionAttributes("cartPay")
public class CheckoutAPIController {

    @ModelAttribute("cartPay")
    public Cart setUpCartPay(@SessionAttribute("cart") Cart cart) {
        Cart cartPay = new Cart();
        cartPay.setId(cart.getId());
        return cartPay;
    }

    @GetMapping("/checkCartPay")
    public ResponseEntity<Boolean> isCartPayEmpty(@ModelAttribute("cartPay") Cart cartPay) {
        if (cartPay.isEmpty()) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> checkout(
            @RequestBody Map<String, List<Integer>> payload,
            @ModelAttribute("cartPay") Cart cartPay,
            @SessionAttribute("cart") Cart cart) {

        List<Integer> itemsId = payload.get("items");

        for (Integer id : itemsId) {
            CartItem cartItem = cart.getCartItem(id.longValue());
            if (cartItem != null) {
                if (cartPay.getCart().contains(cartItem)) {
                    CartItem cartItem1 = cartPay.getCartItem(id.longValue());
                    cartItem1.setQuantity(cartItem1.getQuantity() + cartItem.getQuantity());
                    cartPay.getCart().set(cartPay.getCart().indexOf(cartItem1), cartItem1);
                } else {
                    cartPay.addCartItem(cartItem);
                }

            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
