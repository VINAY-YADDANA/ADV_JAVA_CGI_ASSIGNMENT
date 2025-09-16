package com.example.leavemgmt.service;

import com.example.leavemgmt.entity.LeaveRequest;
import com.example.leavemgmt.entity.LeaveStatus;
import com.example.leavemgmt.repository.LeaveRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveRequestService {
    private final LeaveRequestRepository repository;

    public LeaveRequestService(LeaveRequestRepository repository) {
        this.repository = repository;
    }

    public List<LeaveRequest> findAll() { return repository.findAll(); }

    public List<LeaveRequest> findByStatus(LeaveStatus status) { return repository.findByStatus(status); }

    public List<LeaveRequest> findByEmployeeId(Long employeeId) { return repository.findByEmployeeId(employeeId); }

    public List<LeaveRequest> findByEmployeeIdAndStatus(Long employeeId, LeaveStatus status) {
        return repository.findByEmployeeIdAndStatus(employeeId, status);
    }

    public LeaveRequest save(LeaveRequest leaveRequest) {
        // Always ensure new requests start as PENDING
        if (leaveRequest.getId() == null) {
            leaveRequest.setStatus(LeaveStatus.PENDING);
        }
        return repository.save(leaveRequest);
    }

    public Optional<LeaveRequest> findById(Long id) { return repository.findById(id); }

    public void approve(Long id) {
        repository.findById(id).ifPresent(lr -> {
            lr.setStatus(LeaveStatus.APPROVED);
            repository.save(lr);
        });
    }

    public void reject(Long id) {
        repository.findById(id).ifPresent(lr -> {
            lr.setStatus(LeaveStatus.REJECTED);
            repository.save(lr);
        });
    }

    public long countPending() { return repository.countByStatus(LeaveStatus.PENDING); }
}
