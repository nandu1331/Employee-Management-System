package com.example.Employee_Management_System.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String department;
    private EmployerDTO employer;
    private Set<SkillSetDTO> skills;
    private Set<ProjectDTO> projects;
}
