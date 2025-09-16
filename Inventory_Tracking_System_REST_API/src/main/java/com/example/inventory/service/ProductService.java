package com.example.inventory.service;

import com.example.inventory.entity.Product;
import com.example.inventory.entity.Supplier;
import com.example.inventory.exception.ResourceNotFoundException;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {
  private final ProductRepository productRepository;
  private final SupplierRepository supplierRepository;

  public ProductService(ProductRepository productRepository, SupplierRepository supplierRepository) {
    this.productRepository = productRepository;
    this.supplierRepository = supplierRepository;
  }

  public Product create(Product product, Long supplierId) {
    Supplier supplier = supplierRepository.findById(supplierId)
      .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id " + supplierId));
    product.setSupplier(supplier);
    return productRepository.save(product);
  }

  public List<Product> findAll() {
    return productRepository.findAll();
  }

  public Product findById(Long id) {
    return productRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
  }

  public Product update(Long id, Product input, Long supplierId) {
    Product existing = findById(id);
    existing.setName(input.getName());
    existing.setDescription(input.getDescription());
    existing.setQuantity(input.getQuantity());
    existing.setPrice(input.getPrice());

    if (supplierId != null) {
      Supplier supplier = supplierRepository.findById(supplierId)
        .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id " + supplierId));
      existing.setSupplier(supplier);
    }

    return productRepository.save(existing);
  }

  public void delete(Long id) {
    Product existing = findById(id);
    productRepository.delete(existing);
  }

  public List<Product> findBySupplier(Long supplierId) {
    // throws if supplier doesn't exist (clear error)
    supplierRepository.findById(supplierId)
      .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id " + supplierId));
    return productRepository.findBySupplierId(supplierId);
  }
}
