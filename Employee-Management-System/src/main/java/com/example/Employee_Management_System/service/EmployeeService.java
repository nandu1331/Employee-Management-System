package com.example.Employee_Management_System.service;

import com.example.Employee_Management_System.entity.Employee;
import java.util.List;
import java.util.Set;

public interface EmployeeService {
    Employee createEmployee(Employee employee, Set<Long> skillIds, Set<Long> projectIds);
    Employee updateEmployee(Long employeeId, Employee employee, Set<Long> skillIds, Set<Long> projectIds);
    void deleteEmployee(Long employeeId);
    Employee getEmployeeById(Long employeeId);
    List<Employee> getAllEmployees();
}
