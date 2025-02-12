package com.example.Employee_Management_System.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Getter
@Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Project name is required")
    private String projectName;

    private String description;

    // Back reference for Many-to-Many with Employee
    @ManyToMany(mappedBy = "projects", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Employee> employees;
}

