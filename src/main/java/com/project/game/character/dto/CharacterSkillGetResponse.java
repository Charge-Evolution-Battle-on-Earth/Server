package com.project.game.character.dto;

import com.project.game.skill.domain.Skill;
import lombok.Getter;

@Getter
public class CharacterSkillGetResponse {

    private Long skillId;
    private String skillNm;
    private String description;

    public CharacterSkillGetResponse(Skill skill) {
        this.skillId = skill.getSkillId();
        this.skillNm = skill.getSkillNm();
        this.description = skill.getDescription();
    }
}
