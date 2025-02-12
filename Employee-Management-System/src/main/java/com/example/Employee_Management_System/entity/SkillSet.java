package com.example.Employee_Management_System.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Getter
@Setter
public class SkillSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Skill name is required")
    @Column(unique = true)
    private String skillName;

    @ManyToMany(mappedBy = "skills", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Employee> employees;
}
