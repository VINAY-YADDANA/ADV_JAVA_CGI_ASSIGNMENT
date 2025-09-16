package com.example.leavemgmt.service;

import com.example.leavemgmt.entity.Employee;
import com.example.leavemgmt.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> findAll() { return repository.findAll(); }

    public Optional<Employee> findById(Long id) { return repository.findById(id); }

    public Employee save(Employee employee) { return repository.save(employee); }

    public void deleteById(Long id) { repository.deleteById(id); }

    public boolean emailExists(String email) { return repository.existsByEmail(email); }
}
