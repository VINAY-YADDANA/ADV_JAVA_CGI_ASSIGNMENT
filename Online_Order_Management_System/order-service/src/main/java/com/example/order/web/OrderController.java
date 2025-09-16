package com.example.order.web;

import com.example.order.dto.CreateOrderRequest;
import com.example.order.dto.UpdateStatusRequest;
import com.example.order.model.OrderEntity;
import com.example.order.model.OrderStatus;
import com.example.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrderEntity> placeOrder(@Valid @RequestBody CreateOrderRequest req) {
        OrderEntity saved = service.placeOrder(req);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public List<OrderEntity> all() { return service.findAll(); }

    @GetMapping("/{id}")
    public OrderEntity byId(@PathVariable("id") Long id) { return service.findById(id); }

    @PatchMapping("/{id}/status")
    public OrderEntity updateStatus(@PathVariable("id") Long id, @Valid @RequestBody UpdateStatusRequest req) {
        return service.updateStatus(id, req.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
