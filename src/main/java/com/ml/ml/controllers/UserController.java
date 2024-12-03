package com.ml.ml.controllers;

import com.ml.ml.entities.User;
import com.ml.ml.entities.Cart;
import com.ml.ml.entities.Role;
import com.ml.ml.repositories.CartRepository;
import com.ml.ml.repositories.UserRepository;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    // 1. Registrar um novo usuário
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("O e-mail já está em uso.");
        }

        User savedUser = userRepository.save(user);
        Cart cart = new Cart();
        cart.setUser(savedUser);
        cartRepository.save(cart);

        return ResponseEntity.ok(savedUser);
    }

    // 2. Fazer login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        String account = credentials.get("account");
        String password = credentials.get("password");
    
        Optional<User> user = userRepository.findByEmail(account);
    
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            User loggedInUser = user.get();
            if (loggedInUser.getRole() == Role.ADMIN) {
                return ResponseEntity.ok("Bem-vindo, Administrador!");
            } else {
                return ResponseEntity.ok("Bem-vindo, Consumidor!");
            }
        }
        return ResponseEntity.status(401).body("Credenciais inválidas!");
    }
    

    // 3. Listar todos os usuários (apenas para administradores)
    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestBody String adminAccount) {
        Optional<User> adminUser = userRepository.findByEmail(adminAccount);

        if (adminUser.isPresent() && adminUser.get().getRole() == Role.ADMIN) {
            return ResponseEntity.ok(userRepository.findAll());
        }

        return ResponseEntity.status(403).body("Acesso negado! Somente administradores podem acessar esta rota.");
    }
}
