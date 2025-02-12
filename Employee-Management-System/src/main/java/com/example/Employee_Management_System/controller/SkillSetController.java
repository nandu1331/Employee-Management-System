package com.example.Employee_Management_System.controller;

import com.example.Employee_Management_System.dto.SkillSetDTO;
import com.example.Employee_Management_System.entity.SkillSet;
import com.example.Employee_Management_System.mapper.SkillSetMapper;
import com.example.Employee_Management_System.service.SkillSetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/skills")
@Slf4j
@RequiredArgsConstructor
public class SkillSetController {

    private final SkillSetService skillSetService;

    @PostMapping
    public ResponseEntity<SkillSetDTO> createSkill(@Valid @RequestBody SkillSetDTO skillSetDTO) {
        log.info("Received request to create skill: {}", skillSetDTO);
        SkillSet skillSet = SkillSetMapper.toEntity(skillSetDTO);
        SkillSet createdSkill = skillSetService.createSkillSet(skillSet);
        SkillSetDTO createdSkillDTO = SkillSetMapper.toDTO(createdSkill);
        return new ResponseEntity<>(createdSkillDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillSetDTO> getSkillById(@PathVariable("id") Long skillId) {
        log.info("Received request to get skill with id: {}", skillId);
        SkillSet skillSet = skillSetService.getSkillSetById(skillId);
        SkillSetDTO skillSetDTO = SkillSetMapper.toDTO(skillSet);
        return ResponseEntity.ok(skillSetDTO);
    }

    @GetMapping
    public ResponseEntity<List<SkillSetDTO>> getAllSkills() {
        log.info("Received request to get all skills");
        List<SkillSet> skills = skillSetService.getAllSkillSets();
        List<SkillSetDTO> dtos = skills.stream()
                .map(SkillSetMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillSetDTO> updateSkill(@PathVariable("id") Long skillId,
                                                   @Valid @RequestBody SkillSetDTO skillSetDTO) {
        log.info("Received request to update skill with id: {}", skillId);
        SkillSet skillToUpdate = SkillSetMapper.toEntity(skillSetDTO);
        SkillSet updatedSkill = skillSetService.updateSkillSet(skillId, skillToUpdate);
        SkillSetDTO updatedSkillDTO = SkillSetMapper.toDTO(updatedSkill);
        return ResponseEntity.ok(updatedSkillDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable("id") Long skillId) {
        log.info("Received request to delete skill with id: {}", skillId);
        skillSetService.deleteSkillSet(skillId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
