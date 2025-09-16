package com.example.product.web;

import com.example.product.dto.ProductRequest;
import com.example.product.model.Product;
import com.example.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody ProductRequest req) {
        return new ResponseEntity<>(service.create(req), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Product> all() { return service.findAll(); }

    @GetMapping("/{id}")
    public Product byId(@PathVariable("id") Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public Product update(@PathVariable("id") Long id, @Valid @RequestBody ProductRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/reserve")
    public ResponseEntity<?> reserve(@PathVariable("id") Long id, @RequestParam("qty") int qty) {
        service.reserveStock(id, qty);
        return ResponseEntity.ok(Map.of("status", "reserved", "productId", id, "qty", qty));
    }

    @PostMapping("/{id}/restock")
    public ResponseEntity<?> restock(@PathVariable("id") Long id, @RequestParam("qty") int qty) {
        service.restock(id, qty);
        return ResponseEntity.ok(Map.of("status", "restocked", "productId", id, "qty", qty));
    }

}
