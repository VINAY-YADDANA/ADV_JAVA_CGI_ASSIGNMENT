package com.example.product.service;

import com.example.product.dto.ProductRequest;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product create(ProductRequest req) {
        Product p = new Product(req.getName(), req.getDescription(), req.getPrice(), req.getStockQuantity());
        return repository.save(p);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found: " + id));
    }

    public Product update(Long id, ProductRequest req) {
        Product p = findById(id);
        p.setName(req.getName());
        p.setDescription(req.getDescription());
        p.setPrice(req.getPrice());
        p.setStockQuantity(req.getStockQuantity());
        return repository.save(p);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void reserveStock(Long productId, int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Quantity must be > 0");
        Product p = repository.findWithLockingById(productId);
        if (p == null) throw new RuntimeException("Product not found: " + productId);
        if (p.getStockQuantity() < qty) {
            throw new IllegalStateException("Insufficient stock for product " + productId);
        }
        p.setStockQuantity(p.getStockQuantity() - qty);
        repository.save(p);
    }

    @Transactional
    public void restock(Long productId, int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Quantity must be > 0");
        Product p = repository.findWithLockingById(productId);
        if (p == null) throw new RuntimeException("Product not found: " + productId);
        p.setStockQuantity(p.getStockQuantity() + qty);
        repository.save(p);
    }
}
