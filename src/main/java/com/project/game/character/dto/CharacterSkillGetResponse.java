package com.project.game.character.dto;

import com.project.game.skill.domain.Skill;
import lombok.Getter;

@Getter
public class CharacterSkillGetResponse {

    private Long skillId;
    private String skillType;
    private String skillNm;
    private Integer manaCost;
    private String description;

    public CharacterSkillGetResponse(Skill skill) {
        this.skillId = skill.getSkillId();
        this.skillNm = skill.getSkillNm();
        this.skillType = skill.getSkillType();
        this.manaCost = skill.getManaCost();
        this.description = skill.getDescription();
    }
}
