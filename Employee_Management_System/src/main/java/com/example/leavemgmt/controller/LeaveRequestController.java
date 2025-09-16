package com.example.leavemgmt.controller;

import com.example.leavemgmt.entity.LeaveRequest;
import com.example.leavemgmt.entity.LeaveStatus;
import com.example.leavemgmt.entity.LeaveType;
import com.example.leavemgmt.service.EmployeeService;
import com.example.leavemgmt.service.LeaveRequestService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/leaves")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;
    private final EmployeeService employeeService;

    public LeaveRequestController(LeaveRequestService leaveRequestService, EmployeeService employeeService) {
        this.leaveRequestService = leaveRequestService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public String list(@RequestParam(name = "status", required = false) LeaveStatus status,
                       @RequestParam(name = "employeeId", required = false) Long employeeId,
                       Model model) {
        if (status != null && employeeId != null) {
            model.addAttribute("leaves", leaveRequestService.findByEmployeeIdAndStatus(employeeId, status));
        } else if (status != null) {
            model.addAttribute("leaves", leaveRequestService.findByStatus(status));
        } else if (employeeId != null) {
            model.addAttribute("leaves", leaveRequestService.findByEmployeeId(employeeId));
        } else {
            model.addAttribute("leaves", leaveRequestService.findAll());
        }
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedEmployeeId", employeeId);
        model.addAttribute("statuses", LeaveStatus.values());
        return "leaves/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("leaveRequest", new LeaveRequest());
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("leaveTypes", LeaveType.values());
        return "leaves/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("leaveRequest") LeaveRequest leaveRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("employees", employeeService.findAll());
            model.addAttribute("leaveTypes", LeaveType.values());
            return "leaves/form";
        }
        leaveRequestService.save(leaveRequest);
        return "redirect:/leaves";
    }

    @PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id) {
        leaveRequestService.approve(id);
        return "redirect:/leaves";
    }

    @PostMapping("/{id}/reject")
    public String reject(@PathVariable Long id) {
        leaveRequestService.reject(id);
        return "redirect:/leaves";
    }
}
