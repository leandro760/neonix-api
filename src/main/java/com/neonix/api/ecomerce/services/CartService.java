package com.neonix.api.ecomerce.services;

import com.neonix.api.ecomerce.models.Cart;
import com.neonix.api.ecomerce.models.CartItem;
import com.neonix.api.ecomerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public Optional<Cart> getCartByUserId(String userId) {
        return cartRepository.findByUserId(userId);
    }

    public CartItem addItemToCart(String userId, CartItem cartItem) {
        Cart cart = cartRepository.findByUserId(userId)
            .orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setUserId(userId);
                return cartRepository.save(newCart);
            });
        cartItem.setCart(cart);
        // Aquí podrías agregar lógica para validar o calcular totales
        return cartRepository.save(cart).getItems().add(cartItem); // Nota: Ajusta según tu implementación de CartItem
    }

    public Optional<CartItem> updateCartItem(String userId, Long itemId, CartItem cartItem) {
        Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));
        // Lógica para encontrar y actualizar el item (ajusta según tu modelo)
        return Optional.empty(); // Implementa la lógica para actualizar
    }

    public boolean removeItemFromCart(String userId, Long itemId) {
        Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));
        // Lógica para remover el item (ajusta según tu modelo)
        return true; // Implementa la lógica para remover
    }

    public boolean clearCart(String userId) {
        Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));
        cart.getItems().clear(); // Ajusta según tu implementación
        cartRepository.save(cart);
        return true;
    }
}