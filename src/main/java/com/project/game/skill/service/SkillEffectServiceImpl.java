package com.project.game.skill.service;

import com.project.game.skill.domain.SkillEffect;
import com.project.game.skill.dto.SkillEffectGetListResponse;
import com.project.game.skill.repository.SkillEffectRepository;
import com.project.game.skill.service.usecase.SkillEffectService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillEffectServiceImpl implements SkillEffectService {

    private final SkillEffectRepository skillEffectRepository;

    @Override
    public List<SkillEffectGetListResponse> getSkillEffectList(Long skillId) {
        List<SkillEffect> skillEffects = skillEffectRepository.findBySkillSkillId(skillId);
        List<SkillEffectGetListResponse> skillEffectGetListResponses = skillEffects.stream().map(
            skillEffect -> new SkillEffectGetListResponse(skillEffect)
        ).collect(Collectors.toList());
        return skillEffectGetListResponses;
    }
}