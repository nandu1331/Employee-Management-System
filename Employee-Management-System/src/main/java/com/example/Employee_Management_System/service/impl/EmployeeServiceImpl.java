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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        log.info("Creating employee: {}", employee);
        if (skillIds != null && !skillIds.isEmpty()) {
            Set<SkillSet> skills = skillIds.stream()
                    .map(id -> skillSetRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("SkillSet", "id", id)))
                    .collect(Collectors.toSet());
            employee.setSkills(skills);
        }
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
        log.info("Updating employee with id: {}", employeeId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setRole(employeeDetails.getRole());
        employee.setDepartment(employeeDetails.getDepartment());
        if (employeeDetails.getEmployer() != null) {
            employee.setEmployer(employeeDetails.getEmployer());
        }
        if (skillIds != null) {
            Set<SkillSet> skills = skillIds.stream()
                    .map(id -> skillSetRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("SkillSet", "id", id)))
                    .collect(Collectors.toSet());
            employee.setSkills(skills);
        }
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
        log.info("Deleting employee with id: {}", employeeId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        employeeRepository.delete(employee);
    }

    @Override
//    @Cacheable(value = "employees", key = "#employeeId")
    public Employee getEmployeeById(Long employeeId) {
        log.info("Fetching employee with id: {}", employeeId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        // Force initialization of lazy associations
        if (employee.getEmployer() != null) {
            employee.getEmployer().getName();
        }
        if (employee.getSkills() != null) {
            employee.getSkills().size();
        }
        if (employee.getProjects() != null) {
            employee.getProjects().size();
        }
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        log.info("Fetching all employees");
        List<Employee> employees = employeeRepository.findAll();
        employees.forEach(emp -> {
            if (emp.getEmployer() != null) {
                emp.getEmployer().getName();
            }
            if (emp.getSkills() != null) {
                emp.getSkills().size();
            }
            if (emp.getProjects() != null) {
                emp.getProjects().size();
            }
        });
        return employees;
    }
}
