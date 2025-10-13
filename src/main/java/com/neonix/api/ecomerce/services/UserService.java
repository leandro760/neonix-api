package com.neonix.api.ecomerce.services;

import com.neonix.api.ecomerce.models.User;
import com.neonix.api.ecomerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByClerkUserId(String clerkUserId) {
        return userRepository.findByClerkUserId(clerkUserId);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Integer id, User userDetails) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setFullname(userDetails.getFullname());
            existingUser.setPhone(userDetails.getPhone());
            existingUser.setBirthDate(userDetails.getBirthDate());
            existingUser.setAddress(userDetails.getAddress());
            // No se actualiza clerkUserId directamente a menos que sea un caso de negocio específico
            return userRepository.save(existingUser);
        }
        return null; // O lanzar una excepción
    }

    @Transactional
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void deleteUserByClerkUserId(String clerkUserId) {
        userRepository.deleteByClerkUserId(clerkUserId);
    }
}