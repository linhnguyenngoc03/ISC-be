package com.example.demo.controller;

import com.example.demo.repository.CartItemsRepository;
import com.example.demo.model.CartItems;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cartItem")
public class CartItemController {
    @GetMapping("/allCartItems")
    public ResponseEntity<Object> allCartItem() throws Exception {
        List<CartItems> cartList = CartItemsRepository.getAllCartItems();
        if(cartList.size()>0) return ResponseEntity.ok().body(cartList);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getCartItemsById")
    public ResponseEntity<Object> getCartItemsById(@RequestParam int cartItemId) throws Exception {
        CartItems cartItems = CartItemsRepository.getCartItemsById(cartItemId);
        if(cartItems.getCartItemId() != 0) return ResponseEntity.ok().body(cartItems);
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/createCartItems")
    public ResponseEntity<Object> createCartItems(@RequestBody CartItems cartItems) throws Exception {
        if(CartItemsRepository.createCartItems(cartItems)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/updateCartItems")
    public ResponseEntity<Object> updateCartItems(@RequestBody CartItems cartItems) throws Exception {
        if(CartItemsRepository.updateCartItems(cartItems)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/deleteCartItems")
    public ResponseEntity<Object> deleteCartItem(@RequestParam int[] cartItemId) throws Exception {
        if(CartItemsRepository.deleteCartItem(cartItemId)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/countCartItems")
    public ResponseEntity<Integer> countCartItems(@RequestParam int cartId) throws Exception {
        int count = CartItemsRepository.countCartItems(cartId);
        if(count>0) return ResponseEntity.ok().body(count);
        else return ResponseEntity.badRequest().build();
    }
}
