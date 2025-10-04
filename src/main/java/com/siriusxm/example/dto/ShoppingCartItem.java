package com.siriusxm.example.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GenerationType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;

@Entity
@Table(name = "shoppingCartItem")
public class ShoppingCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Setter
    @Getter
    @Column(name = "title")
    private String title;

    @Setter
    @Getter
    @Column(name = "price")
    private Double price;

    @Setter
    @ManyToOne(fetch=FetchType.EAGER)//Manytoone since many students belong to one department
    @JoinColumn(name="shopping_cart_id",nullable = false)
    private ShoppingCart shoppingCart;

    @Setter
    @Getter
    @Transient
    private int count;

    public ShoppingCartItem(long id, BigDecimal price, ShoppingCart shoppingCart, String title, int count) {
        this.id = id;
        this.price = price.doubleValue();
        this.shoppingCart = shoppingCart;
        this.title = title;
        this.count = count;
    }
}

