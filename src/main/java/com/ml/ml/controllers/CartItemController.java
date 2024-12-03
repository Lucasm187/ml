package com.ml.ml.controllers;

import com.ml.ml.entities.CartItem;
import com.ml.ml.repositories.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    @Autowired
    private CartItemRepository cartItemRepository;

    // 1. Atualizar a quantidade de um item no carrinho
    @PutMapping("/{itemId}")
    public ResponseEntity<CartItem> updateCartItemQuantity(@PathVariable Long itemId, @RequestParam int quantity) {
        Optional<CartItem> cartItem = cartItemRepository.findById(itemId);

        if (cartItem.isPresent() && quantity > 0) {
            CartItem item = cartItem.get();
            item.setQuantity(quantity);
            item.calculateSubtotal(); // Recalcular subtotal após alterar a quantidade
            cartItemRepository.save(item);

            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // 2. Obter detalhes de um item no carrinho
    @GetMapping("/{itemId}")
    public ResponseEntity<CartItem> getCartItem(@PathVariable Long itemId) {
        Optional<CartItem> cartItem = cartItemRepository.findById(itemId);
        return cartItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
