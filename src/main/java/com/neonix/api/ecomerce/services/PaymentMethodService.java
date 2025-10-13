package com.neonix.api.ecomerce.services;

import com.neonix.api.ecomerce.models.PaymentMethod;
import com.neonix.api.ecomerce.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodService {
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    public Optional<PaymentMethod> getPaymentMethodById(Integer id) {
        return paymentMethodRepository.findById(id);
    }

    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethod) {
        return paymentMethodRepository.save(paymentMethod);
    }

    public PaymentMethod updatePaymentMethod(Integer id, PaymentMethod paymentMethodDetails) {
        Optional<PaymentMethod> methodOptional = paymentMethodRepository.findById(id);
        if (methodOptional.isPresent()) {
            PaymentMethod existingMethod = methodOptional.get();
            existingMethod.setPaymentMethod(paymentMethodDetails.getPaymentMethod());
            existingMethod.setDescription(paymentMethodDetails.getDescription());
            existingMethod.setIsActive(paymentMethodDetails.getIsActive());
            existingMethod.setUser(paymentMethodDetails.getUser()); // Puede requerir lógica adicional si el user se actualiza
            return paymentMethodRepository.save(existingMethod);
        }
        return null;
    }

    public void deletePaymentMethod(Integer id) {
        paymentMethodRepository.deleteById(id);
    }
}