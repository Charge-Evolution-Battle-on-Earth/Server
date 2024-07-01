package com.project.game.skill.dto;

import com.project.game.common.domain.StatRate;
import com.project.game.skill.domain.SkillEffect;
import com.project.game.skill.vo.SkillEffectType;

public record SkillEffectGetResponse(String skillNm, Long skillEffectId,
                                     SkillEffectType skillEffectType, Integer fixedValue,
                                     StatRate statRate) {

    public static SkillEffectGetResponse fromEntity(SkillEffect skillEffect) {
        return new SkillEffectGetResponse(skillEffect.getSkill().getSkillNm(),
            skillEffect.getSkillEffectId(),
            skillEffect.getSkillEffectType(), skillEffect.getFixedValue(),
            skillEffect.getStatRate());
    }
}
