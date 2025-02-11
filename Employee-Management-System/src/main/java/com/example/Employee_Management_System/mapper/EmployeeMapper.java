package com.example.Employee_Management_System.mapper;

import com.example.Employee_Management_System.dto.EmployeeDTO;
import com.example.Employee_Management_System.dto.EmployerDTO;
import com.example.Employee_Management_System.dto.SkillSetDTO;
import com.example.Employee_Management_System.dto.ProjectDTO;
import com.example.Employee_Management_System.entity.Employee;
import com.example.Employee_Management_System.entity.Employer;
import com.example.Employee_Management_System.entity.SkillSet;
import com.example.Employee_Management_System.entity.Project;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeMapper {

    public static EmployeeDTO toDTO(Employee employee) {
        if (employee == null) return null;

        // Convert employer
        EmployerDTO employerDTO = EmployerMapper.toDTO(employee.getEmployer());

        // Convert skills
        Set<SkillSetDTO> skillDTOs = null;
        if (employee.getSkills() != null) {
            skillDTOs = employee.getSkills().stream()
                    .map(SkillSetMapper::toDTO)
                    .collect(Collectors.toSet());
        }

        // Convert projects
        Set<ProjectDTO> projectDTOs = null;
        if (employee.getProjects() != null) {
            projectDTOs = employee.getProjects().stream()
                    .map(ProjectMapper::toDTO)
                    .collect(Collectors.toSet());
        }

        return EmployeeDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .role(employee.getRole())
                .department(employee.getDepartment())
                .employer(employerDTO)
                .skills(skillDTOs)
                .projects(projectDTOs)
                .build();
    }

    public static Employee toEntity(EmployeeDTO dto) {
        if (dto == null) return null;
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setRole(dto.getRole());
        employee.setDepartment(dto.getDepartment());

        // Convert employer if provided
        if (dto.getEmployer() != null) {
            employee.setEmployer(EmployerMapper.toEntity(dto.getEmployer()));
        }
        // Note: Mapping of collections (skills, projects) can be handled separately in the service layer.

        return employee;
    }
}
