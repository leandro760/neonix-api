package com.neonix.api.ecomerce.controller;

import com.neonix.api.ecomerce.models.PaymentMethod;
import com.neonix.api.ecomerce.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payment-methods")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @GetMapping
    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethod> getPaymentMethodById(@PathVariable Integer id) {
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(id);
        return paymentMethod.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PaymentMethod> createPaymentMethod(@RequestBody PaymentMethod paymentMethod) {
        paymentMethod.setCreatedAt(LocalDateTime.now());
        PaymentMethod savedPaymentMethod = paymentMethodRepository.save(paymentMethod);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPaymentMethod);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethod> updatePaymentMethod(@PathVariable Integer id, @RequestBody PaymentMethod paymentMethodDetails) {
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(id);
        if (paymentMethod.isPresent()) {
            PaymentMethod existingPaymentMethod = paymentMethod.get();
            existingPaymentMethod.setName(paymentMethodDetails.getName());
            existingPaymentMethod.setDescription(paymentMethodDetails.getDescription());
            existingPaymentMethod.setIsActive(paymentMethodDetails.getIsActive());
            // No actualizar created_at
            PaymentMethod updatedPaymentMethod = paymentMethodRepository.save(existingPaymentMethod);
            return ResponseEntity.ok(updatedPaymentMethod);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable Integer id) {
        if (paymentMethodRepository.existsById(id)) {
            paymentMethodRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}