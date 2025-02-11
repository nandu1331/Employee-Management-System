package com.example.Employee_Management_System.service.impl;

import com.example.Employee_Management_System.entity.Employee;
import com.example.Employee_Management_System.entity.Project;
import com.example.Employee_Management_System.entity.SkillSet;
import com.example.Employee_Management_System.exception.ResourceNotFoundException;
import com.example.Employee_Management_System.repository.EmployeeRepository;
import com.example.Employee_Management_System.repository.ProjectRepository;
import com.example.Employee_Management_System.repository.SkillSetRepository;
import com.example.Employee_Management_System.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final SkillSetRepository skillSetRepository;
    private final ProjectRepository projectRepository;

    @Override
    public Employee createEmployee(Employee employee, Set<Long> skillIds, Set<Long> projectIds) {
        // Set skills if IDs are provided
        if (skillIds != null && !skillIds.isEmpty()) {
            Set<SkillSet> skills = skillIds.stream()
                    .map(id -> skillSetRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("SkillSet", "id", id)))
                    .collect(Collectors.toSet());
            employee.setSkills(skills);
        }
        // Set projects if IDs are provided
        if (projectIds != null && !projectIds.isEmpty()) {
            Set<Project> projects = projectIds.stream()
                    .map(id -> projectRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id)))
                    .collect(Collectors.toSet());
            employee.setProjects(projects);
        }
        return employeeRepository.save(employee);
    }

    @Override
    @CacheEvict(value = "employees", key = "#employeeId")
    public Employee updateEmployee(Long employeeId, Employee employeeDetails, Set<Long> skillIds, Set<Long> projectIds) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));

        // Update basic fields
        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setRole(employeeDetails.getRole());
        employee.setDepartment(employeeDetails.getDepartment());

        if (employeeDetails.getEmployer() != null) {
            employee.setEmployer(employeeDetails.getEmployer());
        }

        // Update skills if provided
        if (skillIds != null) {
            Set<SkillSet> skills = skillIds.stream()
                    .map(id -> skillSetRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("SkillSet", "id", id)))
                    .collect(Collectors.toSet());
            employee.setSkills(skills);
        }

        // Update projects if provided
        if (projectIds != null) {
            Set<Project> projects = projectIds.stream()
                    .map(id -> projectRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id)))
                    .collect(Collectors.toSet());
            employee.setProjects(projects);
        }

        return employeeRepository.save(employee);
    }

    @Override
    @CacheEvict(value = "employees", key = "#employeeId")
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        employeeRepository.delete(employee);
    }

    @Override
    @Cacheable(value = "employees", key = "#employeeId")
    public Employee getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));

        // Force initialization of the lazy-loaded employer
        if (employee.getEmployer() != null) {
            employee.getEmployer().getName();
        }

        // Force initialization of lazy collections
        employee.getSkills().size();
        employee.getProjects().size();
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        // Initialize lazy collections for each employee
        employees.forEach(emp -> {
            emp.getSkills().size();
            emp.getProjects().size();
        });
        return employees;
    }
}
