package com.neonix.api.ecomerce.controller;

import com.neonix.api.ecomerce.models.Order;
import com.neonix.api.ecomerce.models.OrderDetail;
import com.neonix.api.ecomerce.models.Product;
import com.neonix.api.ecomerce.repository.OrderDetailRepository;
import com.neonix.api.ecomerce.repository.OrderRepository;
import com.neonix.api.ecomerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailController {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable Integer id) {
        Optional<OrderDetail> orderDetail = orderDetailRepository.findById(id);
        return orderDetail.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderDetail> createOrderDetail(@RequestBody OrderDetail orderDetail) {
        if (orderDetail.getOrder() == null || orderDetail.getOrder().getId() == null ||
            orderDetail.getProduct() == null || orderDetail.getProduct().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Optional<Order> order = orderRepository.findById(orderDetail.getOrder().getId());
        Optional<Product> product = productRepository.findById(orderDetail.getProduct().getId());

        if (order.isEmpty() || product.isEmpty()) {
            return ResponseEntity.badRequest().body(null); // Order or Product not found
        }

        orderDetail.setOrder(order.get());
        orderDetail.setProduct(product.get());
        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrderDetail);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetail> updateOrderDetail(@PathVariable Integer id, @RequestBody OrderDetail orderDetailDetails) {
        Optional<OrderDetail> orderDetail = orderDetailRepository.findById(id);
        if (orderDetail.isPresent()) {
            OrderDetail existingOrderDetail = orderDetail.get();
            existingOrderDetail.setQuantity(orderDetailDetails.getQuantity());
            existingOrderDetail.setPrice(orderDetailDetails.getPrice());
            // No actualizar order ni product si no se pasan en el request body
            OrderDetail updatedOrderDetail = orderDetailRepository.save(existingOrderDetail);
            return ResponseEntity.ok(updatedOrderDetail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Integer id) {
        if (orderDetailRepository.existsById(id)) {
            orderDetailRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/order/{orderId}")
    public List<OrderDetail> getOrderDetailsByOrderId(@PathVariable Integer orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}