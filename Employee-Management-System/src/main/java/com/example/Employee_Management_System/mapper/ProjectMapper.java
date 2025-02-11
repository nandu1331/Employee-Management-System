package com.example.Employee_Management_System.mapper;

import com.example.Employee_Management_System.dto.ProjectDTO;
import com.example.Employee_Management_System.entity.Project;

public class ProjectMapper {

    public static ProjectDTO toDTO(Project project) {
        if (project == null) return null;
        return ProjectDTO.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .description(project.getDescription())
                .build();
    }

    public static Project toEntity(ProjectDTO dto) {
        if (dto == null) return null;
        Project project = new Project();
        project.setId(dto.getId());
        project.setProjectName(dto.getProjectName());
        project.setDescription(dto.getDescription());
        return project;
    }
}
