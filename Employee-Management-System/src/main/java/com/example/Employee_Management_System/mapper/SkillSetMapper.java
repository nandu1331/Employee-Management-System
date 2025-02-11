package com.example.Employee_Management_System.mapper;

import com.example.Employee_Management_System.dto.SkillSetDTO;
import com.example.Employee_Management_System.entity.SkillSet;

public class SkillSetMapper {

    public static SkillSetDTO toDTO(SkillSet skillSet) {
        if (skillSet == null) return null;
        return SkillSetDTO.builder()
                .id(skillSet.getId())
                .skillName(skillSet.getSkillName())
                .build();
    }

    public static SkillSet toEntity(SkillSetDTO dto) {
        if (dto == null) return null;
        SkillSet skillSet = new SkillSet();
        skillSet.setId(dto.getId());
        skillSet.setSkillName(dto.getSkillName());
        return skillSet;
    }
}
