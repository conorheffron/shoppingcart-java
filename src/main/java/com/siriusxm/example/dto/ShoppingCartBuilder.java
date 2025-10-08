package com.siriusxm.example.dto;

import com.siriusxm.example.model.ShoppingCart;
import com.siriusxm.example.model.ShoppingCartItem;

import java.util.Set;

public class ShoppingCartBuilder {

    private long shoppingCartId;

    private Set<ShoppingCartItem> shoppingCartItem;

    private Double subTotal;

    private Double tax;

    private Double total;

    public ShoppingCartBuilder withShoppingCartId(long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
        return this;
    }

    public ShoppingCartBuilder withShoppingCartItem(Set<ShoppingCartItem> shoppingCartItem) {
        this.shoppingCartItem = shoppingCartItem;
        return this;
    }

    public ShoppingCartBuilder withSubTotal(Double subTotal) {
        this.subTotal = subTotal;
        return this;
    }

    public ShoppingCartBuilder withTax(Double tax) {
        this.tax = tax;
        return this;
    }

    public ShoppingCartBuilder withTotal(Double total) {
        this.total = total;
        return this;
    }

    public ShoppingCart build() {
        return new ShoppingCart(this.shoppingCartId, this.shoppingCartItem, this.subTotal, this.tax, this.total);
    }
}
