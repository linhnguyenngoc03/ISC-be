package com.example.demo.controller;

import com.example.demo.repository.CartRepository;
import com.example.demo.model.Cart;
import com.example.demo.model.CartAndCartItem;
import com.example.demo.model.CartAndCartItemAndProduct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @GetMapping("/allCart")
    public ResponseEntity<Object> getAllCart() throws Exception {
        List<Cart> cartList = CartRepository.getAllCart();
        if(cartList.size()>0) return ResponseEntity.ok().body(cartList);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getCartById")
    public ResponseEntity<Object> getCartByCartId(@RequestParam int cartId) throws Exception {
        Cart cart = CartRepository.getCartByCartId(cartId);
        if(cart.getCartId() != 0) return ResponseEntity.ok().body(cart);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getCartByUserId")
    public ResponseEntity<Object> getCartByUserId(@RequestParam int userId) throws Exception {
        Cart cart = CartRepository.getCartByUserId(userId);
        if(cart.getCartId() != 0) return ResponseEntity.ok().body(cart);
        else return ResponseEntity.badRequest().build();
    }
    @GetMapping("/getCartByUserUid")
    public ResponseEntity<Object> getCartByUserUid(@RequestParam String userUid) throws Exception {
        Cart cart = CartRepository.getCartByUserUid(userUid);
        if (cart.getCartId() != 0) return ResponseEntity.ok().body(cart);
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/createCart")
    public ResponseEntity<Object> createCart(@RequestBody Cart cart) throws Exception {
        if(CartRepository.createCart(cart)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/deleteCart")
    public ResponseEntity<Object> deleteCart(@RequestParam int[] cartId) throws Exception {
        if(CartRepository.deleteCart(cartId)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getCartAndCartItem")
    public ResponseEntity<Object> getCartAndCartItem() throws Exception {
        List<CartAndCartItem> cartAndCartItem = CartRepository.getCartAndCartItem();
        if(cartAndCartItem.size()>0) return ResponseEntity.ok().body(cartAndCartItem);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getCartProductByUserUid")
    public ResponseEntity<Object> getCartProductByUserUid(@RequestParam String userUid) throws Exception {
        CartAndCartItemAndProduct cartAndCartItemAndProduct = CartRepository.getCartProductByUserUid(userUid);
        if(cartAndCartItemAndProduct != null) return ResponseEntity.ok().body(cartAndCartItemAndProduct);
        else return ResponseEntity.badRequest().build();
    }
}
