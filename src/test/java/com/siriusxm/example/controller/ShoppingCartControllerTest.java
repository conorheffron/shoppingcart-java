package com.siriusxm.example.controller;

import com.siriusxm.example.dto.ShoppingCart;
import com.siriusxm.example.dto.ShoppingCartBuilder;
import com.siriusxm.example.service.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class ShoppingCartControllerTest {

    private ShoppingCartController shoppingCartController;

    private ShoppingCartService shoppingCartService;

    @BeforeEach
    public void run() {
        // mocks
        this.shoppingCartService = Mockito.mock(ShoppingCartService.class);

        // controller under test
        this.shoppingCartController = new ShoppingCartController(this.shoppingCartService);
    }

    @Test
    void testGetShoppingCarts() {
        List<ShoppingCart> shoppingCartList = new ArrayList<>();

        when(shoppingCartService.getAll()).thenReturn(shoppingCartList);

        ResponseEntity<List<ShoppingCart>> result = shoppingCartController.getShoppingCarts();

        assertEquals(shoppingCartList, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(this.shoppingCartService, Mockito.times(1)).getAll();
    }

    @Test
    void testGetShoppingCarts_Exception() {
        List<ShoppingCart> shoppingCartList = new ArrayList<>();

        when(shoppingCartService.getAll()).thenThrow(new RuntimeException("Failed to call getAll shipping carts"));

        ResponseEntity<List<ShoppingCart>> result = shoppingCartController.getShoppingCarts();

        assertNull(result.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
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
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        verify(this.shoppingCartService, Mockito.times(1)).post(shoppingCart);
    }

    @Test
    void testPostShoppingCarts_Exception() {
        // given
        ShoppingCart shoppingCart = new ShoppingCartBuilder().build();

        when(this.shoppingCartService.post(shoppingCart)).thenThrow(new RuntimeException("Failed to call post shopping carts"));

        // when
        ResponseEntity<ShoppingCart> result = this.shoppingCartController.postShoppingCarts(shoppingCart);

        // then
        assertNull(result.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        verify(this.shoppingCartService, Mockito.times(1)).post(shoppingCart);
    }
}
