package com.example.Employee_Management_System.controller;

import com.example.Employee_Management_System.dto.EmployeeDTO;
import com.example.Employee_Management_System.entity.Employee;
import com.example.Employee_Management_System.mapper.EmployeeMapper;
import com.example.Employee_Management_System.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
@Slf4j
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        log.info("Received request to create employee: {}", employeeDTO);
        Employee employee = EmployeeMapper.toEntity(employeeDTO);
        Employee createdEmployee = employeeService.createEmployee(employee);
        EmployeeDTO createdEmployeeDTO = EmployeeMapper.toDTO(createdEmployee);
        return new ResponseEntity<>(createdEmployeeDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("id") Long employeeId) {
        log.info("Received request to get employee with id: {}", employeeId);
        Employee employee = employeeService.getEmployeeById(employeeId);
        EmployeeDTO employeeDTO = EmployeeMapper.toDTO(employee);
        return ResponseEntity.ok(employeeDTO);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        log.info("Received request to get all employees");
        List<Employee> employees = employeeService.getAllEmployees();
        List<EmployeeDTO> dtos = employees.stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable("id") Long employeeId,
                                                      @Valid @RequestBody EmployeeDTO employeeDTO) {
        log.info("Received request to update employee with id: {}", employeeId);
        Employee employeeToUpdate = EmployeeMapper.toEntity(employeeDTO);
        Employee updatedEmployee = employeeService.updateEmployee(employeeId, employeeToUpdate);
        EmployeeDTO updatedEmployeeDTO = EmployeeMapper.toDTO(updatedEmployee);
        return ResponseEntity.ok(updatedEmployeeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long employeeId) {
        log.info("Received request to delete employee with id: {}", employeeId);
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
