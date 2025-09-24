package com.neonix.api.ecomerce.services;

import com.neonix.api.ecomerce.models.Order;
import com.neonix.api.ecomerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(Order order) {
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING"); // Estado inicial
        return orderRepository.save(order);
    }

    public Optional<Order> confirmOrder(Long id) {
        return orderRepository.findById(id)
            .map(order -> {
                order.setStatus("CONFIRMED");
                return orderRepository.save(order);
            });
    }

    public Optional<Order> updateOrder(Long id, Order order) {
        return orderRepository.findById(id)
            .map(existingOrder -> {
                existingOrder.setShippingAddress(order.getShippingAddress());
                existingOrder.setPayment(order.getPayment());
                // Actualiza otros campos si es necesario
                return orderRepository.save(existingOrder);
            });
    }

    public boolean cancelOrder(Long id) {
        return orderRepository.findById(id)
            .map(order -> {
                order.setStatus("CANCELLED");
                orderRepository.save(order);
                return true;
            }).orElse(false);
    }
}