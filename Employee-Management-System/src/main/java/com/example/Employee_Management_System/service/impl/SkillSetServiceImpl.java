package com.example.Employee_Management_System.service.impl;

import com.example.Employee_Management_System.entity.SkillSet;
import com.example.Employee_Management_System.exception.ResourceNotFoundException;
import com.example.Employee_Management_System.repository.SkillSetRepository;
import com.example.Employee_Management_System.service.SkillSetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SkillSetServiceImpl implements SkillSetService {

    private final SkillSetRepository skillSetRepository;

    @Override
    public SkillSet createSkillSet(SkillSet skillSet) {
        log.info("Creating skill set: {}", skillSet);
        return skillSetRepository.save(skillSet);
    }

    @Override
    @CacheEvict(value = "skills", key = "#skillId")
    public SkillSet updateSkillSet(Long skillId, SkillSet skillSetDetails) {
        log.info("Updating skill set with id: {}", skillId);
        SkillSet skillSet = skillSetRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("SkillSet", "id", skillId));
        skillSet.setSkillName(skillSetDetails.getSkillName());
        return skillSetRepository.save(skillSet);
    }

    @Override
    @CacheEvict(value = "skills", key = "#skillId")
    public void deleteSkillSet(Long skillId) {
        log.info("Deleting skill set with id: {}", skillId);
        SkillSet skillSet = skillSetRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("SkillSet", "id", skillId));
        skillSetRepository.delete(skillSet);
    }

    @Override
    @Cacheable(value = "skills", key = "#skillId")
    public SkillSet getSkillSetById(Long skillId) {
        log.info("Fetching skill set with id: {}", skillId);
        return skillSetRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("SkillSet", "id", skillId));
    }

    @Override
    public List<SkillSet> getAllSkillSets() {
        log.info("Fetching all skill sets");
        return skillSetRepository.findAll();
    }
}
