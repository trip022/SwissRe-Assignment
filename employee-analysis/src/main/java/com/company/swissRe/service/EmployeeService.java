package com.company.swissRe.service;

import com.company.swissRe.model.Employee;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final Map<Integer, Employee> employeeMap = new HashMap<>();

    // Method to save employees (in memory)
    public List<Employee> saveEmployees(List<Employee> employees) {
        for (Employee employee : employees) {
            // Avoid re-assigning the ID if it's already present
            employeeMap.put(employee.getId(), employee);
        }
        return new ArrayList<>(employeeMap.values());
    }

    // Method to read employee data from a CSV file and save to memory (not database)
    public List<Employee> readEmployeeData(MultipartFile file) throws IOException {
        InputStreamReader reader = new InputStreamReader(file.getInputStream());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(reader);

        List<Employee> employees = new ArrayList<>();

        // First pass to store employees by id
        for (CSVRecord record : records) {
            int id = Integer.parseInt(record.get("Id"));
            String firstName = record.get("firstName");
            String lastName = record.get("lastName");
            double salary = Double.parseDouble(record.get("salary"));
            Integer managerId = record.isSet("managerId") && !record.get("managerId").isEmpty()
                    ? Integer.parseInt(record.get("managerId"))
                    : null;

            // Add employee to the list with the exact ID from CSV
            employees.add(new Employee(id, firstName, lastName, salary, null, managerId));
        }

        // Second pass to assign managers to employees
        for (Employee employee : employees) {
            if (employee.getManagerId() != null) {
                Employee manager = employeeMap.get(employee.getManagerId());
                if (manager != null) {
                    employee.setManager(manager);
                }
            }
        }

        // Save employees into employeeMap (no change to their IDs)
        return saveEmployees(employees);
    }

    // Method to get all employees
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeMap.values());
    }

    // Get reporting manager for a specific employee
    public Employee getReportingManager(int employeeId) {
        Employee employee = employeeMap.get(employeeId);
        if (employee != null && employee.getManager() != null) {
            return employee.getManager();
        }
        return null;
    }

    // Analyzing managers' salaries to check if they earn within the valid range
    public List<String> analyzeManagersSalary(List<Employee> employees) {
        Map<Integer, List<Employee>> managerToSubordinates = employees.stream()
                .filter(emp -> emp.getManager() != null)
                .collect(Collectors.groupingBy(emp -> emp.getManager().getId()));

        List<String> salaryAnalysis = new ArrayList<>();

        for (Employee manager : employees) {
            if (manager.getManager() != null) {
                List<Employee> subordinates = managerToSubordinates.get(manager.getId());

                if (subordinates != null && !subordinates.isEmpty()) {
                    double avgSalary = subordinates.stream().mapToDouble(Employee::getSalary).average().orElse(0);
                    double managerSalary = manager.getSalary();
                    double minSalary = avgSalary * 1.2;
                    double maxSalary = avgSalary * 1.5;

                    if (managerSalary < minSalary) {
                        salaryAnalysis.add(
                                manager.getFirstName() + " earns less than required by " + (minSalary - managerSalary));
                    } else if (managerSalary > maxSalary) {
                        salaryAnalysis.add(
                                manager.getFirstName() + " earns more than required by " + (managerSalary - maxSalary));
                    }
                }
            }
        }

        return salaryAnalysis;
    }

    // Checking if any employees have a reporting line that is too long (more than 4 managers)
    public List<String> checkReportingLineLength(List<Employee> employees) {
        List<String> longReportingLines = new ArrayList<>();

        for (Employee employee : employees) {
            if (employee.getManager() != null) {
                int reportingLineLength = 1;  // Start with the direct manager
                Employee manager = employee.getManager();

                while (manager != null && manager.getManager() != null) {
                    reportingLineLength++;
                    manager = manager.getManager();  // Move up to the next manager

                    if (reportingLineLength > 4) {
                        longReportingLines.add(employee.getFirstName() + " has a reporting line that is too long by "
                                + (reportingLineLength - 4));
                        break;
                    }
                }
            }
        }

        return longReportingLines;
    }
}
