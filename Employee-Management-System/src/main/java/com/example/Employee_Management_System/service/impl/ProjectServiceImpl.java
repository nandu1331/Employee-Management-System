package com.example.Employee_Management_System.service.impl;

import com.example.Employee_Management_System.entity.Project;
import com.example.Employee_Management_System.exception.ResourceNotFoundException;
import com.example.Employee_Management_System.repository.ProjectRepository;
import com.example.Employee_Management_System.service.ProjectService;
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
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public Project createProject(Project project) {
        log.info("Creating project: {}", project);
        return projectRepository.save(project);
    }

    @Override
    @CacheEvict(value = "projects", key = "#projectId")
    public Project updateProject(Long projectId, Project projectDetails) {
        log.info("Updating project with id: {}", projectId);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));
        project.setProjectName(projectDetails.getProjectName());
        project.setDescription(projectDetails.getDescription());
        return projectRepository.save(project);
    }

    @Override
    @CacheEvict(value = "projects", key = "#projectId")
    public void deleteProject(Long projectId) {
        log.info("Deleting project with id: {}", projectId);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));
        projectRepository.delete(project);
    }

    @Override
    @Cacheable(value = "projects", key = "#projectId")
    public Project getProjectById(Long projectId) {
        log.info("Fetching project with id: {}", projectId);
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));
    }

    @Override
    public List<Project> getAllProjects() {
        log.info("Fetching all projects");
        return projectRepository.findAll();
    }
}
