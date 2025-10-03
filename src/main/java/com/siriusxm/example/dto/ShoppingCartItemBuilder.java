package com.siriusxm.example.dto;

import java.math.BigDecimal;

public class ShoppingCartItemBuilder {

    private long id;

    private String title;

    private Double price;

    private int count;

    private ShoppingCart shoppingCart;

    public ShoppingCartItemBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public ShoppingCartItemBuilder withPrice(Double price) {
        this.price = price;
        return this;
    }

    public ShoppingCartItemBuilder withShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        return this;
    }

    public ShoppingCartItemBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public ShoppingCartItemBuilder withCount(int count) {
        this.count = count;
        return this;
    }

    public ShoppingCartItem build() {
        return new ShoppingCartItem(this.id, BigDecimal.valueOf(this.price), this.shoppingCart, this.title, this.count);
    }
}
