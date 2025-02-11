package com.example.Employee_Management_System.service.impl;

import com.example.Employee_Management_System.entity.Employer;
import com.example.Employee_Management_System.exception.ResourceNotFoundException;
import com.example.Employee_Management_System.repository.EmployerRepository;
import com.example.Employee_Management_System.service.EmployerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EmployerServiceImpl implements EmployerService {

    private final EmployerRepository employerRepository;

    @Override
    public Employer createEmployer(Employer employer) {
        log.info("Creating employer: {}", employer);
        return employerRepository.save(employer);
    }

    @Override
    @CacheEvict(value = "employers", key = "#employerId")
    public Employer updateEmployer(Long employerId, Employer employerDetails) {
        log.info("Updating employer with id: {}", employerId);
        Employer employer = employerRepository.findById(employerId)
                .orElseThrow(() -> new ResourceNotFoundException("Employer", "id", employerId));
        employer.setName(employerDetails.getName());
        employer.setAddress(employerDetails.getAddress());
        return employerRepository.save(employer);
    }

    @Override
    @CacheEvict(value = "employers", key = "#employerId")
    public void deleteEmployer(Long employerId) {
        log.info("Deleting employer with id: {}", employerId);
        Employer employer = employerRepository.findById(employerId)
                .orElseThrow(() -> new ResourceNotFoundException("Employer", "id", employerId));
        employerRepository.delete(employer);
    }

    @Override
    @Cacheable(value = "employers", key = "#employerId")
    public Employer getEmployerById(Long employerId) {
        log.info("Fetching employer with id: {}", employerId);
        return employerRepository.findById(employerId)
                .orElseThrow(() -> new ResourceNotFoundException("Employer", "id", employerId));
    }

    @Override
    public List<Employer> getAllEmployers() {
        log.info("Fetching all employers");
        return employerRepository.findAll();
    }
}
