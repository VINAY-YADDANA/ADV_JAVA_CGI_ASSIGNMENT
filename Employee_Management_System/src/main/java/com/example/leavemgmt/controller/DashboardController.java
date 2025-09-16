package com.example.leavemgmt.controller;

import com.example.leavemgmt.repository.EmployeeRepository;
import com.example.leavemgmt.repository.LeaveRequestRepository;
import com.example.leavemgmt.entity.LeaveStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final EmployeeRepository employeeRepository;
    private final LeaveRequestRepository leaveRequestRepository;

    public DashboardController(EmployeeRepository employeeRepository, LeaveRequestRepository leaveRequestRepository) {
        this.employeeRepository = employeeRepository;
        this.leaveRequestRepository = leaveRequestRepository;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        long totalEmployees = employeeRepository.count();
        long totalLeaves = leaveRequestRepository.count();
        long pendingApprovals = leaveRequestRepository.countByStatus(LeaveStatus.PENDING);

        model.addAttribute("totalEmployees", totalEmployees);
        model.addAttribute("totalLeaves", totalLeaves);
        model.addAttribute("pendingApprovals", pendingApprovals);
        return "dashboard";
    }
}
