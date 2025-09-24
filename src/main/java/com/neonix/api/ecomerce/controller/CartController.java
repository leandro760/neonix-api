package com.neonix.api.ecomerce.controller;

import com.neonix.api.ecomerce.models.Cart;
import com.neonix.api.ecomerce.models.CartItem;
import com.neonix.api.ecomerce.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable String userId) {
        return cartService.getCartByUserId(userId)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<CartItem> addItemToCart(@PathVariable String userId, @RequestBody CartItem cartItem) {
        return ResponseEntity.ok(cartService.addItemToCart(userId, cartItem));
    }

    @PutMapping("/{userId}/items/{itemId}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable String userId, @PathVariable Long itemId, @RequestBody CartItem cartItem) {
        return cartService.updateCartItem(userId, itemId, cartItem)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable String userId, @PathVariable Long itemId) {
        if (cartService.removeItemFromCart(userId, itemId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable String userId) {
        if (cartService.clearCart(userId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}