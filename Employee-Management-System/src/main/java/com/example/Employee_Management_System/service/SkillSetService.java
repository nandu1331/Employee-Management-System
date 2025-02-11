package com.example.Employee_Management_System.service;

import com.example.Employee_Management_System.entity.SkillSet;
import java.util.List;

public interface SkillSetService {
    SkillSet createSkillSet(SkillSet skillSet);
    SkillSet updateSkillSet(Long skillId, SkillSet skillSet);
    void deleteSkillSet(Long skillId);
    SkillSet getSkillSetById(Long skillId);
    List<SkillSet> getAllSkillSets();
}
