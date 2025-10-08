package com.siriusxm.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GenerationType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "shoppingCart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shopping_cart_id")
    private long shoppingCartId;

    @OneToMany (fetch = FetchType.EAGER)
    @JoinColumn (name = "shopping_cart_id", referencedColumnName = "shopping_cart_id")
    private Set<ShoppingCartItem> shoppingCartItem;

    @Setter
    @Getter
    @Column(name = "sub_total")
    private Double subTotal;

    @Setter
    @Getter
    @Column(name = "tax")
    private Double tax;

    @Setter
    @Getter
    @Column(name = "total")
    private Double total;

    public ShoppingCart() { }

    public ShoppingCart(long shoppingCartId, Set<ShoppingCartItem> shoppingCartItem, Double subTotal, Double tax, Double total) {
        this.shoppingCartId = shoppingCartId;
        this.shoppingCartItem = shoppingCartItem;
        this.subTotal = subTotal;
        this.tax = tax;
        this.total = total;
    }

    public Set<ShoppingCartItem> getShoppingCartItems() {
        return shoppingCartItem;
    }
}
