package com.codegym.model;


import java.util.ArrayList;
import java.util.List;


public class Cart {

    private Long userId;
    private List<CartItem> cart;
    public Cart() {
        cart = new ArrayList<>();
    }

    public Cart(Long userId) {
        this.userId = userId;
        cart = new ArrayList<>();
    }


    public Long getId() {
        return userId;
    }

    public void setId(Long userId) {
        this.userId = userId;
    }

    public List<CartItem> getCart() {
        return cart;
    }

    public void setCart(List<CartItem> cart) {
        this.cart = cart;
    }

    public Double getTotalPrice() {
        double price = 0D;
        for (CartItem cartItem : cart) {
            price += cartItem.getTotalPrice();
        }
        return price;
    }

    public CartItem getCartItem(Long id) {
        for (CartItem cartItem : cart) {
            if (cartItem.getProduct().getId().equals(id)) {
                return cartItem;
            }
        }
        return null;
    }

    public void updateItem(Long id, int quantity) {
        CartItem cartItem = getCartItem(id);
        if (quantity > 0) {
            cartItem.setQuantity(quantity);
        } else {
            cart.remove(cartItem);
        }
    }

    public void removeCartItem(Long id) {
        CartItem cartItem = getCartItem(id);
        cart.remove(cartItem);
    }

    public void addCartItem(CartItem cartItem) {
        cart.add(cartItem);
    }

    public boolean isEmpty() {
        return cart.isEmpty();
    }

    public void removeCartItems(List<CartItem> cart) {
        List<CartItem> cartCopy = new ArrayList<>(cart);
        for (CartItem cartItem : cartCopy) {
           cart.remove(cartItem);
        }
    }

}
