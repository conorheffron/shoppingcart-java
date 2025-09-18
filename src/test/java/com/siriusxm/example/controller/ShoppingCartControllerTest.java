package com.siriusxm.example.controller;

import com.siriusxm.example.dto.ShoppingCart;
import com.siriusxm.example.dto.ShoppingCartBuilder;
import com.siriusxm.example.service.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class ShoppingCartControllerTest {

    private ShoppingCartController shoppingCartController;

    private ShoppingCartService shoppingCartService;

    @BeforeEach
    public void run() {
        // controller under test
        this.shoppingCartController = new ShoppingCartController();

        // mocks
        this.shoppingCartService = Mockito.mock(ShoppingCartService.class);

        // set mocks
        this.shoppingCartController.setShoppingCartService(this.shoppingCartService);
    }

    @Test
    void testGetShoppingCarts() {
        List<ShoppingCart> shoppingCartList = new ArrayList<>();

        when(shoppingCartService.getAll()).thenReturn(shoppingCartList);

        ResponseEntity<List<ShoppingCart>> result = shoppingCartController.getShoppingCarts();

        assertEquals(shoppingCartList, result.getBody());
        verify(this.shoppingCartService, Mockito.times(1)).getAll();
    }

    @Test
    void testPostShoppingCarts() {
        // given
        ShoppingCart shoppingCart = new ShoppingCartBuilder().build();

        when(this.shoppingCartService.post(shoppingCart)).thenReturn(shoppingCart);

        // when
        ResponseEntity<ShoppingCart> result = this.shoppingCartController.postShoppingCarts(shoppingCart);

        // then
        assertEquals(shoppingCart, result.getBody());
        verify(this.shoppingCartService, Mockito.times(1)).post(shoppingCart);
    }
}
