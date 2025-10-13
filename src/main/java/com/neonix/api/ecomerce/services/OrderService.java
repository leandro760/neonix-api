package com.neonix.api.ecomerce.services;

import com.neonix.api.ecomerce.models.Order;
import com.neonix.api.ecomerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(Integer id, Order orderDetails) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order existingOrder = orderOptional.get();
            existingOrder.setShippingNumber(orderDetails.getShippingNumber());
            existingOrder.setDeliveryDate(orderDetails.getDeliveryDate());
            existingOrder.setOrderValue(orderDetails.getOrderValue());
            existingOrder.setStatus(orderDetails.getStatus());
            existingOrder.setUser(orderDetails.getUser()); // Si el usuario de la orden puede cambiar
            return orderRepository.save(existingOrder);
        }
        return null;
    }

    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }
}