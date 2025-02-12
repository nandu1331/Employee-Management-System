package com.example.Employee_Management_System.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
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
    // Detailed nested objects for response
    @Builder.Default
    private Set<SkillSetDTO> skills = new HashSet<>();
    @Builder.Default
    private Set<ProjectDTO> projects = new HashSet<>();
    // Fields for create/update: association IDs
    @Builder.Default
    private Set<Long> skillIds = new HashSet<>();
    @Builder.Default
    private Set<Long> projectIds = new HashSet<>();
}
