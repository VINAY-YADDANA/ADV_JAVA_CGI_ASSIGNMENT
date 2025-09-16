package com.example.leavemgmt.controller;

import com.example.leavemgmt.entity.Employee;
import com.example.leavemgmt.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employees/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employees/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, Model model) {
        if (employeeService.emailExists(employee.getEmail())) {
            result.rejectValue("email", "duplicate", "Email already exists");
        }
        if (result.hasErrors()) {
            return "employees/form";
        }
        employeeService.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee ID"));
        model.addAttribute("employee", employee);
        return "employees/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("employee") Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            return "employees/form";
        }
        employee.setId(id);
        employeeService.save(employee);
        return "redirect:/employees";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        employeeService.deleteById(id);
        return "redirect:/employees";
    }
}
