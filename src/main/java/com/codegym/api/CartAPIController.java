package com.codegym.api;

import com.codegym.model.Cart;
import com.codegym.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/api/cart")
public class CartAPIController {
    @Autowired
    private IProductService productService;


    @PutMapping("/{id}")
    public ResponseEntity<?> updateCartItem(@SessionAttribute("cart")Cart cart, @PathVariable("id") Long id, @RequestParam("quantity") int quantity) {
        cart.updateItem(id, quantity);
        return new ResponseEntity<>(cart.getCartItem(id).getQuantity(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartItem(@SessionAttribute("cart")Cart cart, @PathVariable("id") Long id) {
        cart.removeCartItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
