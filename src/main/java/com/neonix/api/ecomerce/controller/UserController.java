package com.neonix.api.ecomerce.controller;

import com.neonix.api.ecomerce.models.User;
import com.neonix.api.ecomerce.services.UserService; // Importar el servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService; // Inyectar el servicio

    // Obtener todos los usuarios (solo para administradores o desarrollo)
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        Optional<User> user = userService.findUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener el perfil del usuario autenticado (usando clerk_user_id)
    @GetMapping("/me")
    public ResponseEntity<User> getAuthenticatedUser(@AuthenticationPrincipal Jwt jwt) {
        String clerkUserId = jwt.getSubject(); // O el claim que uses para el ID de Clerk
        Optional<User> user = userService.findUserByClerkUserId(clerkUserId);
        return user.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Registrar un nuevo usuario (posiblemente al recibir un webhook de Clerk o al inicio de sesión)
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user, @AuthenticationPrincipal Jwt jwt) {
        String clerkUserIdFromToken = jwt.getSubject();

        if (userService.userExistsByClerkUserId(clerkUserIdFromToken)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Usuario ya existe
        }

        user.setClerkUserId(clerkUserIdFromToken);
        User savedUser = userService.saveUser(user); // Usar el servicio
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // Actualizar un usuario por ID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails); // Suponiendo un método update en el servicio
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un usuario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        if (userService.userExists(id)) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}