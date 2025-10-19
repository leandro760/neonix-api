package com.neonix.api.ecomerce.controller;

import com.neonix.api.ecomerce.models.Order;
import com.neonix.api.ecomerce.models.User;
import com.neonix.api.ecomerce.models.Address;
import com.neonix.api.ecomerce.models.PaymentMethod;
import com.neonix.api.ecomerce.repository.OrderRepository;
import com.neonix.api.ecomerce.repository.UserRepository;
import com.neonix.api.ecomerce.repository.AddressRepository;
import com.neonix.api.ecomerce.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        if (order.getUser() == null || order.getUser().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Optional<User> user = userRepository.findById(order.getUser().getId());
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(null); // User not found
        }
        order.setUser(user.get());

        if (order.getShippingAddress() != null && order.getShippingAddress().getId() != null) {
            Optional<Address> address = addressRepository.findById(order.getShippingAddress().getId());
            address.ifPresent(order::setShippingAddress);
        }
        if (order.getPaymentMethod() != null && order.getPaymentMethod().getId() != null) {
            Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(order.getPaymentMethod().getId());
            paymentMethod.ifPresent(order::setPaymentMethod);
        }

        order.setOrderDate(LocalDateTime.now());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Integer id, @RequestBody Order orderDetails) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            Order existingOrder = order.get();
            existingOrder.setShippingNumber(orderDetails.getShippingNumber());
            existingOrder.setDeliveryDate(orderDetails.getDeliveryDate());
            existingOrder.setTotalAmount(orderDetails.getTotalAmount());
            existingOrder.setStatus(orderDetails.getStatus());
            existingOrder.setUpdatedAt(LocalDateTime.now());

            if (orderDetails.getShippingAddress() != null && orderDetails.getShippingAddress().getId() != null) {
                Optional<Address> address = addressRepository.findById(orderDetails.getShippingAddress().getId());
                address.ifPresent(existingOrder::setShippingAddress);
            } else if (orderDetails.getShippingAddress() == null) {
                existingOrder.setShippingAddress(null); // Permite establecer la dirección a null
            }

            if (orderDetails.getPaymentMethod() != null && orderDetails.getPaymentMethod().getId() != null) {
                Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(orderDetails.getPaymentMethod().getId());
                paymentMethod.ifPresent(existingOrder::setPaymentMethod);
            } else if (orderDetails.getPaymentMethod() == null) {
                existingOrder.setPaymentMethod(null); // Permite establecer el método de pago a null
            }

            Order updatedOrder = orderRepository.save(existingOrder);
            return ResponseEntity.ok(updatedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable Integer userId) {
        return orderRepository.findByUserId(userId);
    }
}