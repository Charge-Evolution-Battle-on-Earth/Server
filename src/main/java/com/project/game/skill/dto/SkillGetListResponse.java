package com.project.game.skill.dto;

import com.project.game.skill.domain.Skill;
import lombok.Getter;

@Getter
public class SkillGetListResponse {

    private Long skillId;
    private Long levelId;
    private Long nationId;
    private String skillNm;
    private String description;

    public SkillGetListResponse(Skill skill) {
        this.skillId = skill.getSkillId();
        this.levelId = skill.getLevelId();
        this.nationId = skill.getNationId();
        this.skillNm = skill.getSkillNm();
        this.description = skill.getDescription();
    }
}
