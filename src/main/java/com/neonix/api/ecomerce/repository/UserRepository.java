package com.neonix.api.ecomerce.repository;

import com.neonix.api.ecomerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByClerkUserId(String clerkUserId);
    void deleteByClerkUserId(String clerkUserId);
}