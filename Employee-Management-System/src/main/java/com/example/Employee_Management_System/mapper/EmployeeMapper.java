package com.example.Employee_Management_System.mapper;

import com.example.Employee_Management_System.dto.EmployeeDTO;
import com.example.Employee_Management_System.dto.EmployerDTO;
import com.example.Employee_Management_System.dto.SkillSetDTO;
import com.example.Employee_Management_System.dto.ProjectDTO;
import com.example.Employee_Management_System.entity.Employee;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeMapper {

    public static EmployeeDTO toDTO(Employee employee) {
        if (employee == null) return null;

        // Convert employer using its mapper
        EmployerDTO employerDTO = EmployerMapper.toDTO(employee.getEmployer());

        // Convert skills (filter out nulls)
        Set<SkillSetDTO> skillDTOs = new HashSet<>();
        if (employee.getSkills() != null) {
            skillDTOs = employee.getSkills().stream()
                    .map(SkillSetMapper::toDTO)
                    .filter(dto -> dto != null)
                    .collect(Collectors.toSet());
        }

        // Convert projects (filter out nulls)
        Set<ProjectDTO> projectDTOs = new HashSet<>();
        if (employee.getProjects() != null) {
            projectDTOs = employee.getProjects().stream()
                    .map(ProjectMapper::toDTO)
                    .filter(dto -> dto != null)
                    .collect(Collectors.toSet());
        }

        // Optionally populate the ID sets from the entity associations
        Set<Long> skillIds = new HashSet<>();
        if (employee.getSkills() != null) {
            skillIds = employee.getSkills().stream()
                    .map(skill -> skill.getId())
                    .collect(Collectors.toSet());
        }
        Set<Long> projectIds = new HashSet<>();
        if (employee.getProjects() != null) {
            projectIds = employee.getProjects().stream()
                    .map(project -> project.getId())
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
                .skillIds(skillIds)
                .projectIds(projectIds)
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

        if (dto.getEmployer() != null) {
            employee.setEmployer(EmployerMapper.toEntity(dto.getEmployer()));
        }
        // We do not set collections (skills, projects) here.
        return employee;
    }
}
