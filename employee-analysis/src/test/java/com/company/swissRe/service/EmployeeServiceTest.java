package com.company.swissRe.service;

import com.company.swissRe.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService();
    }

    @Test
    void testSaveEmployees() {
        Employee employee = new Employee(123, "Joe", "Doe", 60000, null, null);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        List<Employee> savedEmployees = employeeService.saveEmployees(employees);

        assertEquals(1, savedEmployees.size());
        assertEquals("Joe", savedEmployees.get(0).getFirstName());
    }

    @Test
    void testGetReportingManager() {
        Employee manager = new Employee(123, "Joe", "Doe", 60000, null, null);
        Employee employee = new Employee(124, "Martin", "Chekov", 45000, manager, 123);

        employeeService.saveEmployees(List.of(manager, employee));

        Employee reportingManager = employeeService.getReportingManager(124);

        assertNotNull(reportingManager);
        assertEquals("Joe", reportingManager.getFirstName());
    }

    @Test
    void testAnalyzeManagersSalary() {
        Employee manager = new Employee(123, "Joe", "Doe", 60000, null, null);
        Employee subordinate = new Employee(124, "Martin", "Chekov", 45000, manager, 123);

        employeeService.saveEmployees(List.of(manager, subordinate));

        List<String> analysis = employeeService.analyzeManagersSalary(List.of(manager, subordinate));

        assertTrue(analysis.isEmpty());
    }

    @Test
    void testCheckReportingLineLength() {
        Employee manager = new Employee(123, "Joe", "Doe", 60000, null, null);
        Employee subordinate = new Employee(124, "Martin", "Chekov", 45000, manager, 123);

        employeeService.saveEmployees(List.of(manager, subordinate));

        List<String> longReportingLines = employeeService.checkReportingLineLength(List.of(manager, subordinate));

        assertTrue(longReportingLines.isEmpty());
    }
}
