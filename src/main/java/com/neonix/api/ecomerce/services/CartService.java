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
        cart.getItems().add(cartItem); // Agrega el item al carrito
        cartRepository.save(cart); // Guarda el carrito actualizado
        return cartItem; // Retorna el CartItem agregado
    }

    public Optional<CartItem> updateCartItem(String userId, Long itemId, CartItem cartItem) {
        Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));
        // Lógica para encontrar y actualizar el item
        for (CartItem item : cart.getItems()) {
            if (item.getId().equals(itemId)) {
                item.setQuantity(cartItem.getQuantity());
                item.setProduct(cartItem.getProduct());
                cartRepository.save(cart);
                return Optional.of(item);
            }
        }
        return Optional.empty(); // Si no se encuentra el item
    }

    public boolean removeItemFromCart(String userId, Long itemId) {
        Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));
        boolean removed = cart.getItems().removeIf(item -> item.getId().equals(itemId));
        if (removed) {
            cartRepository.save(cart);
        }
        return removed;
    }

    public boolean clearCart(String userId) {
        Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));
        cart.getItems().clear(); // Ajusta según tu implementación
        cartRepository.save(cart);
        return true;
    }
}