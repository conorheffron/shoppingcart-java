package com.siriusxm.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siriusxm.example.dto.ShoppingCart;
import com.siriusxm.example.dto.ShoppingCartBuilder;
import com.siriusxm.example.dto.ShoppingCartItem;
import com.siriusxm.example.dto.ShoppingCartItemBuilder;
import com.siriusxm.example.repository.ShoppingCartItemRepository;
import com.siriusxm.example.repository.ShoppingCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.MockitoAnnotations.openMocks;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class ShoppingCartServiceTest {

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    public void run() {
        openMocks(this);
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
        shoppingCartService.setObjectMapper(new ObjectMapper());
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

    @Test
    public void testGetAllReturnsShoppingCarts() {
        ShoppingCart cart = new ShoppingCart();
        when(shoppingCartRepository.findAll()).thenReturn(List.of(cart));

        List<ShoppingCart> result = shoppingCartService.getAll();

        assertEquals(1, result.size());
    }

    @Test
    public void testPostSetsTaxAndItems() {
        ShoppingCartItem item = new ShoppingCartItemBuilder().withPrice(10.0).withTitle("cheerios").build();
        ShoppingCart cart = new ShoppingCartBuilder().withShoppingCartItem(Set.of(item)).build();
        ShoppingCart savedCart = new ShoppingCartBuilder().withShoppingCartId(1L).build();

        when(shoppingCartRepository.save(cart)).thenReturn(savedCart);

        ShoppingCart result = shoppingCartService.post(cart);

        assertEquals(savedCart, result);

        verify(shoppingCartItemRepository, times(1)).save(any());
        assertEquals(10.0, cart.getSubTotal(), 0.01);
        assertEquals(1.5, cart.getTax(), 0.01);
        assertEquals(11.5, cart.getTotal(), 0.01);
    }

    @Test
    public void testFetchPriceThrowsRuntimeException() {
        ShoppingCartService service = spy(shoppingCartService);

        doThrow(new RuntimeException("IO Error")).when(service).fetchPrice(anyString());

        assertThrows(RuntimeException.class, () -> service.fetchPrice("cheerios"));
    }

    @Test
    public void testFetchPriceThrowsRuntimeException_FailedToReadJSON() throws IOException {
        shoppingCartService.setObjectMapper(objectMapper);

        doThrow(new RuntimeException("Mapper Error")).when(objectMapper).readTree(any(InputStream.class));

        assertThrows(RuntimeException.class, () -> shoppingCartService.fetchPrice("cheerios"));
    }

    @Test
    public void testSubtotalTaxTotal() {
        ShoppingCartItem item1 = new ShoppingCartItemBuilder().withPrice(2.5).withCount(2).build();
        ShoppingCartItem item2 = new ShoppingCartItemBuilder().withPrice(1.0).withCount(3).build();

        ShoppingCart cart = new ShoppingCartBuilder().withShoppingCartItem(Set.of(item1, item2)).build();

        BigDecimal expectedSubtotal = BigDecimal.valueOf(2.5 * 2 + 1.0 * 3).setScale(2, RoundingMode.UP);
        BigDecimal expectedTax = expectedSubtotal.multiply(BigDecimal.valueOf(0.125)).setScale(2, RoundingMode.UP);
        BigDecimal expectedTotal = expectedSubtotal.add(expectedTax).setScale(2, RoundingMode.UP);

        assertEquals(expectedSubtotal, shoppingCartService.subtotal(cart));
        assertEquals(expectedTax, shoppingCartService.tax(cart));
        assertEquals(expectedTotal, shoppingCartService.total(cart));
    }

    @Test
    public void testSubtotalEmptyCart() {
        ShoppingCart cart = new ShoppingCartBuilder().withShoppingCartItem(Collections.emptySet()).build();

        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.UP), shoppingCartService.subtotal(cart));
    }
}
