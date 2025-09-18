package com.siriusxm.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siriusxm.example.dto.ShoppingCart;
import com.siriusxm.example.dto.ShoppingCartItem;
import com.siriusxm.example.dto.ShoppingCartItemBuilder;
import com.siriusxm.example.repository.ShoppingCartItemRepository;
import com.siriusxm.example.repository.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.List;

@Setter
@Slf4j
@Service
public class ShoppingCartService {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String BASE_URL = "https://raw.githubusercontent.com/mattjanks16/shopping-cart-test-data/main/";
    private static final double TAX_RATE = 0.125;

    // Supported products
    public static final String[] PRODUCTS = { "cheerios", "cornflakes", "frosties", "shreddies", "weetabix" };

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Transactional
    public List<ShoppingCart> getAll() {
        return shoppingCartRepository.findAll();
    }

    @Transactional
    public ShoppingCart post(ShoppingCart shoppingCart) {
        setTax(shoppingCart);

        ShoppingCart save = shoppingCartRepository.save(shoppingCart);

        setShoppingCartItems(shoppingCart, save);

        return save;
    }

    private void setTax(ShoppingCart shoppingCart) {
        double subTotal = 0d;
        for (ShoppingCartItem shoppingCartItem : shoppingCart.getShoppingCartItems())
            subTotal += shoppingCartItem.getPrice();
        shoppingCart.setSubTotal(subTotal);
        double tax = subTotal * .15;
        shoppingCart.setTax(tax);
        shoppingCart.setTotal(subTotal + tax);
    }

    private void setShoppingCartItems(ShoppingCart shoppingCart, ShoppingCart save) {
        shoppingCart.getShoppingCartItems().forEach(c -> {
            shoppingCartItemRepository.save(new ShoppingCartItemBuilder()
                    .withShoppingCart(save)
                    .withTitle(c.getTitle())
                    .withPrice(c.getPrice())
                    .build());
        });
    }

    // Fetches price for a product (by name), throws Exception if not found or parsing fails
    public double fetchPrice(String product) {
        String url = BASE_URL + product.toLowerCase() + ".json";
        try (InputStream is = new URL(url).openStream()) {
            JsonNode node = objectMapper.readTree(is);
            return node.get("price").asDouble();
        } catch (IOException e) {
            log.error(String.format("Unexpected exception occurred while fetching product=%s.", product), e);
            throw new RuntimeException(e);
        }
    }

    public BigDecimal subtotal(ShoppingCart shoppingCart) {
        return shoppingCart.getShoppingCartItems().stream()
                .map(item -> BigDecimal.valueOf(item.getPrice()).multiply(BigDecimal.valueOf(item.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.UP);
    }

    public BigDecimal tax(ShoppingCart shoppingCart) {
        return subtotal(shoppingCart).multiply(BigDecimal.valueOf(TAX_RATE)).setScale(2, RoundingMode.UP);
    }

    public BigDecimal total(ShoppingCart shoppingCart) {
        return subtotal(shoppingCart).add(tax(shoppingCart)).setScale(2, RoundingMode.UP);
    }
}
