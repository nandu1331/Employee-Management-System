package com.example.Employee_Management_System.service;

import com.example.Employee_Management_System.entity.Project;
import java.util.List;

public interface ProjectService {
    Project createProject(Project project);
    Project updateProject(Long projectId, Project project);
    void deleteProject(Long projectId);
    Project getProjectById(Long projectId);
    List<Project> getAllProjects();
}
