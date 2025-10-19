package com.neonix.api.ecomerce.services;

import com.neonix.api.ecomerce.models.OrderDetail;
import com.neonix.api.ecomerce.repository.OrderDetailRepository;
import com.neonix.api.ecomerce.repository.OrderRepository;
import com.neonix.api.ecomerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<OrderDetail> findAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    public Optional<OrderDetail> findOrderDetailById(Integer id) {
        return orderDetailRepository.findById(id);
    }

    public List<OrderDetail> findOrderDetailsByOrderId(Integer orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
        // Asegurarse de que las referencias a Order y Product son gestionadas por JPA
        if (orderDetail.getOrder() != null && orderDetail.getOrder().getId() != null) {
            orderRepository.findById(orderDetail.getOrder().getId()).ifPresent(orderDetail::setOrder);
        }
        if (orderDetail.getProduct() != null && orderDetail.getProduct().getId() != null) {
            productRepository.findById(orderDetail.getProduct().getId()).ifPresent(orderDetail::setProduct);
        }
        return orderDetailRepository.save(orderDetail);
    }

    public OrderDetail updateOrderDetail(Integer id, OrderDetail orderDetailDetails) {
        Optional<OrderDetail> existingOrderDetail = orderDetailRepository.findById(id);
        if (existingOrderDetail.isPresent()) {
            OrderDetail orderDetail = existingOrderDetail.get();
            orderDetail.setQuantity(orderDetailDetails.getQuantity());
            orderDetail.setPrice(orderDetailDetails.getPrice());
            // No se suelen cambiar Order ni Product en una actualización de OrderDetail, solo cantidad y precio.
            return orderDetailRepository.save(orderDetail);
        }
        return null; // O lanzar una excepción
    }

    public void deleteOrderDetail(Integer id) {
        orderDetailRepository.deleteById(id);
    }

    public boolean orderDetailExists(Integer id) {
        return orderDetailRepository.existsById(id);
    }
}