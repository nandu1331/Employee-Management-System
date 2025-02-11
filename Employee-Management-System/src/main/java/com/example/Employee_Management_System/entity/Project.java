package com.example.Employee_Management_System.entity;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Project name is required")
    private String projectName;

    private String description;

    // Back reference for Many-to-Many with Employee
    @ManyToMany(mappedBy = "projects", fetch = FetchType.LAZY)
    private Set<Employee> employees;
}

