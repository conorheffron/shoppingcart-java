package com.siriusxm.example.controller;

import com.siriusxm.example.model.ShoppingCart;
import com.siriusxm.example.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ShoppingCartController {

  private final ShoppingCartService shoppingCartService;

  @Autowired
  public ShoppingCartController(ShoppingCartService shoppingCartService) {
      this.shoppingCartService = shoppingCartService;
  }

    @GetMapping("/shoppingCart")
  public ResponseEntity<List<ShoppingCart>> getShoppingCarts() {
    try {
      List<ShoppingCart> shoppingCartList = shoppingCartService.getAll();

      return new ResponseEntity<>(shoppingCartList, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>((HttpHeaders) null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/shoppingCart")
  public ResponseEntity<ShoppingCart> postShoppingCarts(@RequestBody ShoppingCart shoppingCart) {
    try {
      ShoppingCart shoppingCartResponse = shoppingCartService.post(shoppingCart);

      return new ResponseEntity<>(shoppingCartResponse, HttpStatus.ACCEPTED);
    } catch (Exception e) {
      return new ResponseEntity<>((HttpHeaders) null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
