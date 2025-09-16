package com.example.inventory.controller;

import com.example.inventory.entity.Product;
import com.example.inventory.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping("/supplier/{supplierId}")
  @ResponseStatus(HttpStatus.CREATED)
  public Product create(@PathVariable Long supplierId, @Valid @RequestBody Product product) {
    return productService.create(product, supplierId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Product createWithQuery(@RequestParam Long supplierId, @Valid @RequestBody Product product) {
    return productService.create(product, supplierId);
  }

  @GetMapping
  public List<Product> getAll(@RequestParam(required = false) Long supplierId) {
    if (supplierId != null) return productService.findBySupplier(supplierId);
    return productService.findAll();
  }

  @GetMapping("/{id}")
  public Product getById(@PathVariable Long id) {
    return productService.findById(id);
  }

  @PutMapping("/{id}")
  public Product update(@PathVariable Long id,
                        @RequestParam(required = false) Long supplierId,
                        @Valid @RequestBody Product product) {
    return productService.update(id, product, supplierId);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    productService.delete(id);
  }
}
