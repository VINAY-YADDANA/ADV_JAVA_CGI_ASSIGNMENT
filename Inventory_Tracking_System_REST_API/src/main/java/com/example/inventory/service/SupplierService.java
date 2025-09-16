package com.example.inventory.service;

import com.example.inventory.entity.Supplier;
import com.example.inventory.exception.ResourceNotFoundException;
import com.example.inventory.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SupplierService {
  private final SupplierRepository supplierRepository;

  public SupplierService(SupplierRepository supplierRepository) {
    this.supplierRepository = supplierRepository;
  }

  public Supplier create(Supplier supplier) {
    return supplierRepository.save(supplier);
  }

  public List<Supplier> findAll() {
    return supplierRepository.findAll();
  }

  public Supplier findById(Long id) {
    return supplierRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id " + id));
  }

  public Supplier update(Long id, Supplier updated) {
    Supplier existing = findById(id);
    existing.setName(updated.getName());
    existing.setEmail(updated.getEmail());
    existing.setPhoneNumber(updated.getPhoneNumber());
    existing.setAddress(updated.getAddress());
    return supplierRepository.save(existing);
  }

  public void delete(Long id) {
    Supplier existing = findById(id);
    supplierRepository.delete(existing);
  }
}
