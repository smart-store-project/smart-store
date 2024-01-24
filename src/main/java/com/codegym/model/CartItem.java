package com.codegym.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class CartItem {
    private Product product;
    private Long id;

    private int quantity;

    public CartItem() {
    }

    public CartItem(Long id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}
