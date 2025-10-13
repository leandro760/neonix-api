package com.neonix.api.ecomerce.services;

import com.neonix.api.ecomerce.models.OrderDetail;
import com.neonix.api.ecomerce.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    public Optional<OrderDetail> getOrderDetailById(Integer id) {
        return orderDetailRepository.findById(id);
    }

    public OrderDetail createOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    public OrderDetail updateOrderDetail(Integer id, OrderDetail orderDetailDetails) {
        Optional<OrderDetail> detailOptional = orderDetailRepository.findById(id);
        if (detailOptional.isPresent()) {
            OrderDetail existingDetail = detailOptional.get();
            existingDetail.setQuantity(orderDetailDetails.getQuantity());
            existingDetail.setSalePrice(orderDetailDetails.getSalePrice());
            existingDetail.setOrderNumber(orderDetailDetails.getOrderNumber());
            existingDetail.setOrder(orderDetailDetails.getOrder());
            existingDetail.setProduct(orderDetailDetails.getProduct());
            existingDetail.setPaymentMethod(orderDetailDetails.getPaymentMethod());
            return orderDetailRepository.save(existingDetail);
        }
        return null;
    }

    public void deleteOrderDetail(Integer id) {
        orderDetailRepository.deleteById(id);
    }
}