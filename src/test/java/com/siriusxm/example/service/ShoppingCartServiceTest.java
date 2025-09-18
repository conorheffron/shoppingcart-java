package com.siriusxm.example.service;

import com.siriusxm.example.dto.ShoppingCart;
import com.siriusxm.example.dto.ShoppingCartBuilder;
import com.siriusxm.example.dto.ShoppingCartItem;
import com.siriusxm.example.dto.ShoppingCartItemBuilder;
import com.siriusxm.example.repository.ShoppingCartItemRepository;
import com.siriusxm.example.repository.ShoppingCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class ShoppingCartServiceTest {

    private ShoppingCartService shoppingCartService;
    private ShoppingCartRepository shoppingCartRepository;
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @BeforeEach
    public void run() {
        // service under test
        shoppingCartService = new ShoppingCartService();

        // mocks
        shoppingCartRepository = Mockito.mock(ShoppingCartRepository.class);
        shoppingCartItemRepository = Mockito.mock(ShoppingCartItemRepository.class);

        // set mocks
        this.shoppingCartService.setShoppingCartRepository(this.shoppingCartRepository);
        this.shoppingCartService.setShoppingCartItemRepository(this.shoppingCartItemRepository);
    }

    @Test
    public void testGetAll() {
        List<ShoppingCart> shoppingCartList = new ArrayList<>();
        shoppingCartList.add(new ShoppingCartBuilder().build());
        when(this.shoppingCartRepository.findAll()).thenReturn(shoppingCartList);

        List<ShoppingCart> all = this.shoppingCartService.getAll();

        assertEquals(shoppingCartList, all);

        verify(this.shoppingCartRepository).findAll();
        verifyNoMoreInteractions(this.shoppingCartRepository);
        verifyNoInteractions(this.shoppingCartItemRepository);
    }

    @Test
    public void testPost() {
        // given (the following test parameters and variables)
        BigDecimal testPrice = new BigDecimal(1);
        String testTitle = "title";

        ShoppingCartItem shoppingCartItem = new ShoppingCartItemBuilder()
                .withPrice(testPrice.doubleValue())
                .withTitle(testTitle)
                .build();

        long test_id = 0L;
        ShoppingCart shoppingCart = new ShoppingCartBuilder()
                .withShoppingCartId(test_id)
                .withShoppingCartItem(new HashSet<>(Collections.singleton(shoppingCartItem)))
                .build();

        when(this.shoppingCartRepository.save(Mockito.eq(shoppingCart))).thenReturn(shoppingCart);

        // when (test unit or 'post' method is run)
        ShoppingCart result = this.shoppingCartService.post(shoppingCart);


        // then (check result and behaviour of function)
        assertEquals(shoppingCart, result);

        verify(this.shoppingCartRepository, times(1)).save(Mockito.eq(shoppingCart));
        verify(this.shoppingCartItemRepository, Mockito.atLeastOnce()).save(Mockito.any(ShoppingCartItem.class));
    }

    @Test
    void testSampleCartCalculation() {
        // given
        // Add 2 × cornflakes @ 2.52 each
        ShoppingCartItem shoppingCartItemCf = new ShoppingCartItemBuilder()
                .withTitle("cornflakes")
                .withCount(2)
                .withPrice(shoppingCartService.fetchPrice("cornflakes"))
                .build();
        // Add 1 × weetabix @ 9.98 each
        ShoppingCartItem shoppingCartItemWb = new ShoppingCartItemBuilder()
                .withTitle("weetabix")
                .withCount(1)
                .withPrice(shoppingCartService.fetchPrice("weetabix"))
                .build();

        ShoppingCart shoppingCart = new ShoppingCartBuilder()
                .withShoppingCartItem(new HashSet<>(Arrays.asList(shoppingCartItemCf, shoppingCartItemWb)))
                .build();

        // when, then
        assertEquals(new BigDecimal("15.02"), shoppingCartService.subtotal(shoppingCart),
                "Subtotal should match sample where Subtotal = 15.02");
        assertEquals(new BigDecimal("1.88"), shoppingCartService.tax(shoppingCart),
                "Tax should match sample where Tax = 1.88");
        assertEquals(new BigDecimal("16.90"), shoppingCartService.total(shoppingCart),
                "Total should match sample where Total = 16.90");
    }
}
