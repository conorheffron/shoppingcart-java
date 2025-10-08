package com.siriusxm.example.controller;

import com.siriusxm.example.config.TypicalJobConfiguration;
import com.siriusxm.example.dto.ShoppingCart;
import com.siriusxm.example.dto.ShoppingCartBuilder;
import com.siriusxm.example.dto.ShoppingCartItem;
import com.siriusxm.example.dto.ShoppingCartItemBuilder;
import com.siriusxm.example.repository.ShoppingCartItemRepository;
import com.siriusxm.example.repository.ShoppingCartRepository;
import com.siriusxm.example.service.ShoppingCartService;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShoppingCartControllerMvcTest {

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @InjectMocks
    private ShoppingCartController shoppingCartController;

    @MockitoBean
    private ShoppingCartService shoppingCartService;

    @MockitoBean
    private EntityManagerFactory entityManagerFactory;

    @MockitoBean
    private TypicalJobConfiguration typicalJobConfiguration;

    @MockitoBean
    private ShoppingCartRepository shoppingCartRepository;

    @MockitoBean
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @MockitoBean
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @Test
    void test_get_empty_shopping_cart_success() throws Exception {
        // given
        when(shoppingCartService.getAll()).thenReturn(Collections.emptyList());

        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/api/shoppingCart").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        // then
        verify(shoppingCartService).getAll();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is("[]"));
    }

    @Test
    void test_get_shopping_cart_success() throws Exception {
        // given
        when(shoppingCartService.getAll()).thenReturn(List.of(new ShoppingCartBuilder()
                .withShoppingCartId(1L)
                        .withSubTotal(22.75)
                        .withTax(2.84)
                        .withTotal(25.59)
                 .withShoppingCartItem(Set.of(new ShoppingCartItemBuilder()
                                 .withTitle("nesquik")
                                 .withPrice(3.25)
                                 .withId(123L)
                                 .withCount(7).build()))
                         .build()));

        // when
        MockHttpServletResponse response = mockMvc.perform(
                        get("/api/shoppingCart").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").isArray())
                .andExpect(jsonPath("$.[*]").value(hasSize(1)))
                .andExpect(jsonPath("$.[*].shoppingCartItems.[*]").value(hasSize(1)))
                .andExpect(jsonPath("$.[*].subTotal").value(22.75))
                .andExpect(jsonPath("$.[*].tax").value(2.84))
                .andExpect(jsonPath("$.[*].total").value(25.59))
                .andExpect(jsonPath("$.[*].shoppingCartItems.[*].title").value(hasItem("nesquik")))
                .andExpect(jsonPath("$.[*].shoppingCartItems.[*].count").value(hasItem(7)))
                .andExpect(jsonPath("$.[*].shoppingCartItems.[*].price").value(hasItem(3.25)))
                .andReturn().getResponse();

        // then
        verify(shoppingCartService).getAll();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
    }

    @Test
    void test_get_shopping_cart_success_with_sample_data() throws Exception {
        // given
        ShoppingCartItem shoppingCartItemCf = new ShoppingCartItemBuilder()
                .withId(99L)
                .withTitle("cornflakes")
                .withCount(2)
                .withPrice(2.52)
                .build();
        ShoppingCartItem shoppingCartItemWb = new ShoppingCartItemBuilder()
                .withId(100L)
                .withTitle("weetabix")
                .withPrice(9.98)
                .withCount(1)
                .build();

        ShoppingCart shoppingCart = new ShoppingCartBuilder()
                .withShoppingCartItem(new HashSet<>(Arrays.asList(shoppingCartItemCf, shoppingCartItemWb)))
                .withSubTotal(15.02)
                .withTax(1.88)
                .withTotal(16.90)
                .build();

        when(shoppingCartService.getAll()).thenReturn(List.of(shoppingCart));

        // when
        MockHttpServletResponse response = mockMvc.perform(
                        get("/api/shoppingCart").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").isArray())
                .andExpect(jsonPath("$.[*]").value(hasSize(1)))
                .andExpect(jsonPath("$.[*].shoppingCartItems.[*]").value(hasSize(2)))
                .andExpect(jsonPath("$.[*].subTotal").value(15.02))
                .andExpect(jsonPath("$.[*].tax").value(1.88))
                .andExpect(jsonPath("$.[*].total").value(16.90))
                .andExpect(jsonPath("$.[*].shoppingCartItems.[*].title").value(containsInAnyOrder("cornflakes", "weetabix")))
                .andExpect(jsonPath("$.[*].shoppingCartItems.[*].count").value(containsInAnyOrder(2, 1)))
                .andExpect(jsonPath("$.[*].shoppingCartItems.[*].price").value(containsInAnyOrder(2.52, 9.98)))
                .andReturn().getResponse();

        // then
        verify(shoppingCartService).getAll();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
    }
}
