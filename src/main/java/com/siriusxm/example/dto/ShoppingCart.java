package com.siriusxm.example.dto;

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
    private long shopping_cart_id;

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

    public ShoppingCart(long shopping_cart_id, Set<ShoppingCartItem> shoppingCartItem, Double subTotal, Double tax, Double total) {
        this.shopping_cart_id = shopping_cart_id;
        this.shoppingCartItem = shoppingCartItem;
        this.subTotal = subTotal;
        this.tax = tax;
        this.total = total;
    }

    public Set<ShoppingCartItem> getShoppingCartItems() {
        return shoppingCartItem;
    }

    public void setShoppingCartItems(Set<ShoppingCartItem> shoppingCartItems) {
        this.shoppingCartItem = shoppingCartItems;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id=" + shopping_cart_id +
                ", shoppingCartItems=" + shoppingCartItem +
                ", subTotal=" + subTotal +
                ", tax=" + tax +
                ", total=" + total +
                '}';
    }
}
