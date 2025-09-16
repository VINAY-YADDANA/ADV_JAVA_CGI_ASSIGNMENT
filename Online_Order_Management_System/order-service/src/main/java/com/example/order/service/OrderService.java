package com.example.order.service;

import com.example.order.dto.CreateOrderRequest;
import com.example.order.integration.ProductClient;
import com.example.order.model.OrderEntity;
import com.example.order.model.OrderStatus;
import com.example.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ProductClient productClient;

    public OrderService(OrderRepository repository, ProductClient productClient) {
        this.repository = repository;
        this.productClient = productClient;
    }

    @Transactional
    public OrderEntity placeOrder(CreateOrderRequest req) {
        productClient.getProduct(req.getProductId());

        productClient.reserve(req.getProductId(), req.getQuantity());

        OrderEntity order = new OrderEntity(
                req.getProductId(),
                req.getQuantity(),
                req.getCustomerName(),
                OrderStatus.PENDING,
                LocalDateTime.now()
        );
        return repository.save(order);
    }

    public List<OrderEntity> findAll() { return repository.findAll(); }

    public OrderEntity findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Order not found. Please enter valid: " + id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public OrderEntity updateStatus(Long id, OrderStatus newStatus) {
        OrderEntity order = findById(id);
        OrderStatus old = order.getStatus();
        order.setStatus(newStatus);

        // If cancelling an order that was previously not cancelled, restock
        if (newStatus == OrderStatus.CANCELLED && old != OrderStatus.CANCELLED) {
            productClient.restock(order.getProductId(), order.getQuantity());
        }
        return repository.save(order);
    }
}
