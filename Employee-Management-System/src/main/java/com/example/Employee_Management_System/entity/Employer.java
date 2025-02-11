package com.example.Employee_Management_System.entity;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "employers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Employer name is required")
    private String name;

    private String address;

    // One employer can have many employees
    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Employee> employees;
}
