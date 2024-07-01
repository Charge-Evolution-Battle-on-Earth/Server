package com.project.game.skill.service;

import com.project.game.skill.domain.SkillEffect;
import com.project.game.skill.dto.SkillEffectGetListResponse;
import com.project.game.skill.dto.SkillEffectGetResponse;
import com.project.game.skill.dto.SkillEffectUpsertRequest;
import com.project.game.skill.repository.SkillEffectRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SkillEffectServiceImpl implements SkillEffectService {

    private final SkillEffectRepository skillEffectRepository;

    @Override
    public List<SkillEffectGetListResponse> getSkillEffectList(Long skillId) {
        List<SkillEffect> skillEffects = skillEffectRepository.findBySkillSkillId(skillId);
        return skillEffects.stream().map(SkillEffectGetListResponse::new).toList();
    }

    @Override
    @Transactional
    public void update(SkillEffectUpsertRequest dto) {
        SkillEffect skillEffect = skillEffectRepository.findById(dto.skillEffectId())
            .orElseThrow(RuntimeException::new);
        skillEffect.updateFixedValue(dto.fixedValue());
        skillEffect.updateStatRate(dto.statRate());
    }

    @Override
    public List<SkillEffectGetResponse> getAllSkillEffectList() {
        List<SkillEffect> skillEffects = skillEffectRepository.findAll();
        return skillEffects.stream().map(SkillEffectGetResponse::fromEntity).toList();
    }
}