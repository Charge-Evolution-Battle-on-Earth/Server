package com.project.game.skill.controller;

import com.project.game.skill.dto.SkillEffectGetListResponse;
import com.project.game.skill.dto.SkillEffectGetResponse;
import com.project.game.skill.dto.SkillEffectUpsertRequest;
import com.project.game.skill.dto.SkillGetListResponse;
import com.project.game.skill.service.SkillEffectService;
import com.project.game.skill.service.SkillService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/skills")
public class SkillController {

    private final SkillService skillService;
    private final SkillEffectService skillEffectService;

    @GetMapping
    private ResponseEntity<List<SkillGetListResponse>> findAllSkills() {
        List<SkillGetListResponse> response = skillService.getSkillList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/effects")
    private ResponseEntity<List<SkillEffectGetResponse>> findAllSkillEffects() {
        List<SkillEffectGetResponse> response = skillEffectService.getAllSkillEffectList();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    private ResponseEntity<Void> updateSkillEffect(@RequestBody SkillEffectUpsertRequest dto) {
        skillEffectService.update(dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/effects/{skillId}")
    private ResponseEntity<List<SkillEffectGetListResponse>> findAllSkillsEffect(
        @PathVariable Long skillId) {
        List<SkillEffectGetListResponse> response = skillEffectService.getSkillEffectList(skillId);
        return ResponseEntity.ok(response);
    }
}
