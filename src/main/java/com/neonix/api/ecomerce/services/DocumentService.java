package com.neonix.api.ecomerce.services;

import com.neonix.api.ecomerce.models.Document;
import com.neonix.api.ecomerce.models.User;
import com.neonix.api.ecomerce.repository.DocumentRepository;
import com.neonix.api.ecomerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Document> findAllDocuments() {
        return documentRepository.findAll();
    }

    public Optional<Document> findDocumentById(Integer id) {
        return documentRepository.findById(id);
    }

    public List<Document> findDocumentsByUserId(Integer userId) {
        return documentRepository.findByUserId(userId);
    }

    public Document saveDocument(Document document) {
        if (document.getId() == null) {
            document.setCreatedAt(LocalDateTime.now());
        }
        // Asegurarse de que la referencia al usuario es gestionada por JPA
        if (document.getUser() != null && document.getUser().getId() != null) {
            Optional<User> user = userRepository.findById(document.getUser().getId());
            user.ifPresent(document::setUser);
        }
        return documentRepository.save(document);
    }

    public Document updateDocument(Integer id, Document documentDetails) {
        Optional<Document> existingDocument = documentRepository.findById(id);
        if (existingDocument.isPresent()) {
            Document document = existingDocument.get();
            document.setDocumentName(documentDetails.getDocumentName());
            document.setDocumentUrl(documentDetails.getDocumentUrl());
            document.setDocumentData(documentDetails.getDocumentData());
            // created_at no se actualiza, user_id no se cambia en una actualización de documento
            return documentRepository.save(document);
        }
        return null; // O lanzar una excepción
    }

    public void deleteDocument(Integer id) {
        documentRepository.deleteById(id);
    }

    public boolean documentExists(Integer id) {
        return documentRepository.existsById(id);
    }
}