package com.neonix.api.ecomerce.services;

import com.neonix.api.ecomerce.models.Order;
import com.neonix.api.ecomerce.repository.AddressRepository;
import com.neonix.api.ecomerce.repository.OrderRepository;
import com.neonix.api.ecomerce.repository.PaymentMethodRepository;
import com.neonix.api.ecomerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> findOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    public List<Order> findOrdersByUserId(Integer userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order saveOrder(Order order) {
        if (order.getId() == null) {
            order.setOrderDate(LocalDateTime.now());
            order.setCreatedAt(LocalDateTime.now());
        }
        order.setUpdatedAt(LocalDateTime.now());

        // Asegurarse de que las referencias a User, Address y PaymentMethod son gestionadas por JPA
        if (order.getUser() != null && order.getUser().getId() != null) {
            userRepository.findById(order.getUser().getId()).ifPresent(order::setUser);
        }
        if (order.getShippingAddress() != null && order.getShippingAddress().getId() != null) {
            addressRepository.findById(order.getShippingAddress().getId()).ifPresent(order::setShippingAddress);
        } else {
            order.setShippingAddress(null); // Permite desvincular
        }
        if (order.getPaymentMethod() != null && order.getPaymentMethod().getId() != null) {
            paymentMethodRepository.findById(order.getPaymentMethod().getId()).ifPresent(order::setPaymentMethod);
        } else {
            order.setPaymentMethod(null); // Permite desvincular
        }

        return orderRepository.save(order);
    }

    public Order updateOrder(Integer id, Order orderDetails) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            Order order = existingOrder.get();
            order.setShippingNumber(orderDetails.getShippingNumber());
            order.setDeliveryDate(orderDetails.getDeliveryDate());
            order.setTotalAmount(orderDetails.getTotalAmount());
            order.setStatus(orderDetails.getStatus());
            order.setUpdatedAt(LocalDateTime.now());

            // Actualizar referencias si se proporcionan o desvincular si se pasa null
            if (orderDetails.getShippingAddress() != null && orderDetails.getShippingAddress().getId() != null) {
                addressRepository.findById(orderDetails.getShippingAddress().getId()).ifPresent(order::setShippingAddress);
            } else if (orderDetails.getShippingAddress() == null) {
                order.setShippingAddress(null);
            }

            if (orderDetails.getPaymentMethod() != null && orderDetails.getPaymentMethod().getId() != null) {
                paymentMethodRepository.findById(orderDetails.getPaymentMethod().getId()).ifPresent(order::setPaymentMethod);
            } else if (orderDetails.getPaymentMethod() == null) {
                order.setPaymentMethod(null);
            }

            return orderRepository.save(order);
        }
        return null; // O lanzar una excepción
    }

    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }

    public boolean orderExists(Integer id) {
        return orderRepository.existsById(id);
    }
}