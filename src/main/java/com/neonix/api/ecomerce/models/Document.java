package com.neonix.api.ecomerce.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "document_name", nullable = false)
    private String documentName;

    @Column(name = "document_url")
    private String documentUrl;

    @Column(name = "document_data", columnDefinition = "TEXT")
    private String documentData;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getDocumentData() {
        return documentData;
    }

    public void setDocumentData(String documentData) {
        this.documentData = documentData;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Object getName() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

    public void setName(Object name) {
        
        throw new UnsupportedOperationException("Unimplemented method 'setName'");
    }
}