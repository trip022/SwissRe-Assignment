package com.company.swissRe.controller;

import com.company.swissRe.model.Employee;
import com.company.swissRe.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Endpoint to fetch all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // Endpoint to read employee data from CSV and save to the database (with MultipartFile)
    @PostMapping("/upload")
    public List<Employee> importEmployees(@RequestParam("file") MultipartFile file) throws IOException {
        return employeeService.readEmployeeData(file);
    }

    // Endpoint to analyze managers' salary discrepancies
    @GetMapping("/salary-analysis")
    public List<String> analyzeManagersSalary() {
        return employeeService.analyzeManagersSalary(employeeService.getAllEmployees());
    }

    // Endpoint to check employees' reporting line length
    @GetMapping("/reporting-line")
    public List<String> checkReportingLineLength() {
        return employeeService.checkReportingLineLength(employeeService.getAllEmployees());
    }
}
