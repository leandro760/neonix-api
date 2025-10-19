package com.neonix.api.ecomerce.repository;

import com.neonix.api.ecomerce.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
    List<Document> findByUserId(Integer userId);
}