package com.example.inventory.controller;

import com.example.inventory.entity.Supplier;
import com.example.inventory.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

  private final SupplierService supplierService;

  public SupplierController(SupplierService supplierService) {
    this.supplierService = supplierService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Supplier create(@Valid @RequestBody Supplier supplier) {
    return supplierService.create(supplier);
  }

  @GetMapping
  public List<Supplier> getAll() {
    return supplierService.findAll();
  }

  @GetMapping("/{id}")
  public Supplier getById(@PathVariable Long id) {
    return supplierService.findById(id);
  }

  @PutMapping("/{id}")
  public Supplier update(@PathVariable Long id, @Valid @RequestBody Supplier supplier) {
    return supplierService.update(id, supplier);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    supplierService.delete(id);
  }
}
