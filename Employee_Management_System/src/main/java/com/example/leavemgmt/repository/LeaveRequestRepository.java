package com.example.leavemgmt.repository;

import com.example.leavemgmt.entity.LeaveRequest;
import com.example.leavemgmt.entity.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByStatus(LeaveStatus status);
    List<LeaveRequest> findByEmployeeId(Long employeeId);
    List<LeaveRequest> findByEmployeeIdAndStatus(Long employeeId, LeaveStatus status);
    long countByStatus(LeaveStatus status);
}
