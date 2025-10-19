package com.neonix.api.ecomerce.services;

import com.neonix.api.ecomerce.models.User;
import com.neonix.api.ecomerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByClerkUserId(String clerkUserId) {
        return userRepository.findByClerkUserId(clerkUserId);
    }

    public User saveUser(User user) {
        if (user.getId() == null) { // Nuevo usuario
            user.setRegistrationDate(LocalDateTime.now());
            user.setCreatedAt(LocalDateTime.now());
        }
        user.setUpdatedAt(LocalDateTime.now()); // Siempre actualizar en cada guardado
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public boolean userExists(Integer id) {
        return userRepository.existsById(id);
    }

    public boolean userExistsByClerkUserId(String clerkUserId) {
        return userRepository.findByClerkUserId(clerkUserId).isPresent();
    }

    public User updateUser(Integer id, User userDetails) {
        
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }
}