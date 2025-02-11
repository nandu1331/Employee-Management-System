package com.example.Employee_Management_System.entity;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "skill_sets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Skill name is required")
    @Column(unique = true)
    private String skillName;

    // Back reference for Many-to-Many with Employee
    @ManyToMany(mappedBy = "skills", fetch = FetchType.LAZY)
    private Set<Employee> employees;
}
