package com.project.game.skill.dto;

import com.project.game.common.domain.Stat;
import com.project.game.common.domain.StatRate;
import com.project.game.skill.domain.SkillEffect;
import jakarta.persistence.Embedded;
import java.util.List;
import lombok.Getter;

@Getter
public class SkillEffectGetListResponse {

    private Boolean target;

    private Integer turns;

    private Stat stat;

    private StatRate statRate;

    public SkillEffectGetListResponse(SkillEffect skillEffect) {
        this.target = skillEffect.getTarget();
        this.turns = skillEffect.getTurns();
        this.stat = skillEffect.getStat();
        this.statRate = skillEffect.getStatRate();
    }
}
