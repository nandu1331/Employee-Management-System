package com.example.Employee_Management_System.controller;

import com.example.Employee_Management_System.dto.EmployerDTO;
import com.example.Employee_Management_System.entity.Employer;
import com.example.Employee_Management_System.mapper.EmployerMapper;
import com.example.Employee_Management_System.service.EmployerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employers")
@Slf4j
@RequiredArgsConstructor
public class EmployerController {

    private final EmployerService employerService;

    @PostMapping
    public ResponseEntity<EmployerDTO> createEmployer(@Valid @RequestBody EmployerDTO employerDTO) {
        log.info("Received request to create employer: {}", employerDTO);
        Employer employer = EmployerMapper.toEntity(employerDTO);
        Employer createdEmployer = employerService.createEmployer(employer);
        EmployerDTO createdEmployerDTO = EmployerMapper.toDTO(createdEmployer);
        return new ResponseEntity<>(createdEmployerDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployerDTO> getEmployerById(@PathVariable("id") Long employerId) {
        log.info("Received request to get employer with id: {}", employerId);
        Employer employer = employerService.getEmployerById(employerId);
        EmployerDTO employerDTO = EmployerMapper.toDTO(employer);
        return ResponseEntity.ok(employerDTO);
    }

    @GetMapping
    public ResponseEntity<List<EmployerDTO>> getAllEmployers() {
        log.info("Received request to get all employers");
        List<Employer> employers = employerService.getAllEmployers();
        List<EmployerDTO> dtos = employers.stream()
                .map(EmployerMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployerDTO> updateEmployer(@PathVariable("id") Long employerId,
                                                      @Valid @RequestBody EmployerDTO employerDTO) {
        log.info("Received request to update employer with id: {}", employerId);
        Employer employerToUpdate = EmployerMapper.toEntity(employerDTO);
        Employer updatedEmployer = employerService.updateEmployer(employerId, employerToUpdate);
        EmployerDTO updatedEmployerDTO = EmployerMapper.toDTO(updatedEmployer);
        return ResponseEntity.ok(updatedEmployerDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployer(@PathVariable("id") Long employerId) {
        log.info("Received request to delete employer with id: {}", employerId);
        employerService.deleteEmployer(employerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
