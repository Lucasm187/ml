package com.ml.ml.controllers;

import com.ml.ml.dto.CartDTO;
import com.ml.ml.dto.CartItemDTO;
import com.ml.ml.entities.Cart;
import com.ml.ml.entities.CartItem;
import com.ml.ml.entities.Book;
import com.ml.ml.repositories.CartRepository;
import com.ml.ml.repositories.CartItemRepository;
import com.ml.ml.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BookRepository bookRepository;

    // Método para converter Cart para CartDTO
    private CartDTO convertToDTO(Cart cart) {
        List<CartItemDTO> items = cart.getItems().stream()
                .map(item -> new CartItemDTO(
                        item.getId(),
                        item.getBook().getTitle(),
                        item.getQuantity(),
                        item.getSubtotal()
                ))
                .collect(Collectors.toList());

        return new CartDTO(
                cart.getId(),
                items,
                cart.getTotalAmount()
        );
    }

    // 1. Visualizar o carrinho
    @GetMapping("/{cartId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Long cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        return cart.map(value -> ResponseEntity.ok(convertToDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 2. Adicionar um item ao carrinho
    @PostMapping("/{cartId}/add")
    public ResponseEntity<CartDTO> addItemToCart(@PathVariable Long cartId, @RequestParam Long bookId, @RequestParam int quantity) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        Optional<Book> book = bookRepository.findById(bookId);

        if (cart.isPresent() && book.isPresent()) {
            CartItem newItem = new CartItem();
            newItem.setBook(book.get());
            newItem.setQuantity(quantity);
            newItem.calculateSubtotal();

            Cart existingCart = cart.get();
            existingCart.addItem(newItem);

            cartItemRepository.save(newItem);
            cartRepository.save(existingCart);

            return ResponseEntity.ok(convertToDTO(existingCart));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // 3. Remover um item do carrinho
    @DeleteMapping("/{cartId}/remove/{itemId}")
    public ResponseEntity<CartDTO> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        Optional<CartItem> item = cartItemRepository.findById(itemId);

        if (cart.isPresent() && item.isPresent()) {
            Cart existingCart = cart.get();
            CartItem cartItem = item.get();

            existingCart.removeItem(cartItem);

            cartItemRepository.delete(cartItem);
            cartRepository.save(existingCart);

            return ResponseEntity.ok(convertToDTO(existingCart));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}