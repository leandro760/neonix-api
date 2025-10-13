package com.neonix.api.ecomerce.services;

import com.neonix.api.ecomerce.models.Document;
import com.neonix.api.ecomerce.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Optional<Document> getDocumentById(Integer id) {
        return documentRepository.findById(id);
    }

    public Document createDocument(Document document) {
        return documentRepository.save(document);
    }

    public Document updateDocument(Integer id, Document documentDetails) {
        Optional<Document> documentOptional = documentRepository.findById(id);
        if (documentOptional.isPresent()) {
            Document existingDocument = documentOptional.get();
            existingDocument.setName(documentDetails.getName());
            existingDocument.setDocumentData(documentDetails.getDocumentData());
            existingDocument.setUser(documentDetails.getUser()); // Si el user puede cambiar
            return documentRepository.save(existingDocument);
        }
        return null;
    }

    public void deleteDocument(Integer id) {
        documentRepository.deleteById(id);
    }
}