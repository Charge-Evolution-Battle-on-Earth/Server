package com.project.game.skill.dto;

import com.project.game.common.domain.StatRate;

public record SkillEffectUpsertRequest(Long skillEffectId, Integer fixedValue, StatRate statRate) {

}
