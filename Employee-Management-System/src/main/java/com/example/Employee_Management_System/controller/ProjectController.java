package com.example.Employee_Management_System.controller;

import com.example.Employee_Management_System.dto.ProjectDTO;
import com.example.Employee_Management_System.entity.Project;
import com.example.Employee_Management_System.mapper.ProjectMapper;
import com.example.Employee_Management_System.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
@Slf4j
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@Valid @RequestBody ProjectDTO projectDTO) {
        log.info("Received request to create project: {}", projectDTO);
        Project project = ProjectMapper.toEntity(projectDTO);
        Project createdProject = projectService.createProject(project);
        ProjectDTO createdProjectDTO = ProjectMapper.toDTO(createdProject);
        return new ResponseEntity<>(createdProjectDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable("id") Long projectId) {
        log.info("Received request to get project with id: {}", projectId);
        Project project = projectService.getProjectById(projectId);
        ProjectDTO projectDTO = ProjectMapper.toDTO(project);
        return ResponseEntity.ok(projectDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        log.info("Received request to get all projects");
        List<Project> projects = projectService.getAllProjects();
        List<ProjectDTO> dtos = projects.stream()
                .map(ProjectMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable("id") Long projectId,
                                                    @Valid @RequestBody ProjectDTO projectDTO) {
        log.info("Received request to update project with id: {}", projectId);
        Project projectToUpdate = ProjectMapper.toEntity(projectDTO);
        Project updatedProject = projectService.updateProject(projectId, projectToUpdate);
        ProjectDTO updatedProjectDTO = ProjectMapper.toDTO(updatedProject);
        return ResponseEntity.ok(updatedProjectDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable("id") Long projectId) {
        log.info("Received request to delete project with id: {}", projectId);
        projectService.deleteProject(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
