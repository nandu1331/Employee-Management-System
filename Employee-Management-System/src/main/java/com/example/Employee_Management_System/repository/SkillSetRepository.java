package com.example.Employee_Management_System.repository;

import com.example.Employee_Management_System.entity.SkillSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillSetRepository extends JpaRepository<SkillSet, Long> {
    SkillSet findBySkillName(String skillName);
}
