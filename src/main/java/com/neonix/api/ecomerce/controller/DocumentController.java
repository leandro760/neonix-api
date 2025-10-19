package com.neonix.api.ecomerce.controller;

import com.neonix.api.ecomerce.models.Document;
import com.neonix.api.ecomerce.models.User;
import com.neonix.api.ecomerce.repository.DocumentRepository;
import com.neonix.api.ecomerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Integer id) {
        Optional<Document> document = documentRepository.findById(id);
        return document.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Document> createDocument(@RequestBody Document document) {
        if (document.getUser() == null || document.getUser().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Optional<User> user = userRepository.findById(document.getUser().getId());
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(null); // User not found
        }
        document.setUser(user.get());
        document.setCreatedAt(LocalDateTime.now());
        Document savedDocument = documentRepository.save(document);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDocument);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable Integer id, @RequestBody Document documentDetails) {
        Optional<Document> document = documentRepository.findById(id);
        if (document.isPresent()) {
            Document existingDocument = document.get();
            existingDocument.setDocumentName(documentDetails.getDocumentName());
            existingDocument.setDocumentUrl(documentDetails.getDocumentUrl());
            existingDocument.setDocumentData(documentDetails.getDocumentData());
            // No actualizar created_at
            Document updatedDocument = documentRepository.save(existingDocument);
            return ResponseEntity.ok(updatedDocument);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Integer id) {
        if (documentRepository.existsById(id)) {
            documentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public List<Document> getDocumentsByUserId(@PathVariable Integer userId) {
        return documentRepository.findByUserId(userId);
    }
}