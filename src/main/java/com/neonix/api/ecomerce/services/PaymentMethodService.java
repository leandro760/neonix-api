package com.neonix.api.ecomerce.services;

import com.neonix.api.ecomerce.models.PaymentMethod;
import com.neonix.api.ecomerce.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public List<PaymentMethod> findAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    public Optional<PaymentMethod> findPaymentMethodById(Integer id) {
        return paymentMethodRepository.findById(id);
    }

    public PaymentMethod savePaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod.getId() == null) {
            paymentMethod.setCreatedAt(LocalDateTime.now());
        }
        return paymentMethodRepository.save(paymentMethod);
    }

    public PaymentMethod updatePaymentMethod(Integer id, PaymentMethod paymentMethodDetails) {
        Optional<PaymentMethod> existingPaymentMethod = paymentMethodRepository.findById(id);
        if (existingPaymentMethod.isPresent()) {
            PaymentMethod paymentMethod = existingPaymentMethod.get();
            paymentMethod.setName(paymentMethodDetails.getName());
            paymentMethod.setDescription(paymentMethodDetails.getDescription());
            paymentMethod.setIsActive(paymentMethodDetails.getIsActive());
            // created_at no se actualiza
            return paymentMethodRepository.save(paymentMethod);
        }
        return null; // O lanzar una excepción
    }

    public void deletePaymentMethod(Integer id) {
        paymentMethodRepository.deleteById(id);
    }

    public boolean paymentMethodExists(Integer id) {
        return paymentMethodRepository.existsById(id);
    }
}