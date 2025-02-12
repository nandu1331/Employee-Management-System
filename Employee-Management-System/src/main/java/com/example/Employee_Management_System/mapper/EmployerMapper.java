package com.example.Employee_Management_System.mapper;

import com.example.Employee_Management_System.dto.EmployerDTO;
import com.example.Employee_Management_System.entity.Employer;

public class EmployerMapper {
    public static EmployerDTO toDTO(Employer employer) {
        if (employer == null) return null;
        return EmployerDTO.builder()
                .id(employer.getId())
                .name(employer.getName())
                .address(employer.getAddress())
                .build();
    }

    public static Employer toEntity(EmployerDTO dto) {
        if (dto == null) return null;
        Employer employer = new Employer();
        employer.setId(dto.getId());
        employer.setName(dto.getName());
        employer.setAddress(dto.getAddress());
        return employer;
    }
}
