package com.example.Employee_Management_System.service;

import com.example.Employee_Management_System.entity.Employer;
import java.util.List;

public interface EmployerService {
    Employer createEmployer(Employer employer);
    Employer updateEmployer(Long employerId, Employer employer);
    void deleteEmployer(Long employerId);
    Employer getEmployerById(Long employerId);
    List<Employer> getAllEmployers();
}
