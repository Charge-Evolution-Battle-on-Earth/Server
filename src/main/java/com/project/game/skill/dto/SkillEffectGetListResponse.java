package com.project.game.skill.dto;

import com.project.game.common.domain.StatRate;
import com.project.game.skill.domain.SkillEffect;
import com.project.game.skill.vo.SkillEffectType;
import lombok.Getter;

@Getter
public class SkillEffectGetListResponse {

    private SkillEffectType skillEffectType;
    private Integer fixedValue;
    private StatRate statRate;

    public SkillEffectGetListResponse(SkillEffect skillEffect) {
        this.skillEffectType = skillEffect.getSkillEffectType();
        this.fixedValue = skillEffect.getFixedValue();
        this.statRate = skillEffect.getStatRate();
    }
}
