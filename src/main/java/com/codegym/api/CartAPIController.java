package com.codegym.api;

import com.codegym.model.Cart;
import com.codegym.model.CartItem;
import com.codegym.model.Product;
import com.codegym.model.dto.UserDto;
import com.codegym.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/cart")
@SessionAttributes("cart")
public class CartAPIController {
    @Autowired
    private IProductService productService;


    @ModelAttribute("user")
    private UserDto currentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println("session" + session.getAttribute("user"));
        return (UserDto) session.getAttribute("user");
    }

    @ModelAttribute("cart")
    public Cart setupCart(@ModelAttribute("user") UserDto userDto) {
        return new Cart(userDto.getId());
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateCartItem(@SessionAttribute("cart") Cart cart, @PathVariable("id") Long id, @RequestParam("quantity") int quantity) {
        cart.updateItem(id, quantity);
        return new ResponseEntity<>(cart.getCartItem(id).getQuantity(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartItem(@SessionAttribute("cart") Cart cart, @PathVariable("id") Long id) {
        cart.removeCartItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<?> addToCart(@PathVariable("productId") Long productId,
                                       @ModelAttribute("cart") Cart cart,
                                       @ModelAttribute("user") UserDto userDto) {
        System.out.println("user " + userDto.getUsername());
        if (userDto == null) {
            return new ResponseEntity<>("Not login", HttpStatus.FORBIDDEN);

        }
        Product product = productService.findById(productId);
        if (product != null) {
            // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa (phải kiểm tra để không nó thêm mới sản phẩm)
            CartItem existingItem = cart.getCartItem(productId);
            if (existingItem != null) {
                // Nếu đã có, cập nhật số lượng(+1 sản phẩm)
                existingItem.setQuantity(existingItem.getQuantity() + 1);
            } else {
                // Nếu chưa có, thêm mới sản phẩm vào giỏ hàng
                CartItem cartItem = new CartItem(product, 1);
                cart.addCartItem(cartItem);
            }
            return new ResponseEntity<>("Product added to cart", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }
}
