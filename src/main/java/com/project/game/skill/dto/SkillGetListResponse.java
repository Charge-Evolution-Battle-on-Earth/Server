package com.project.game.skill.dto;

import com.project.game.nation.domain.Nation;
import com.project.game.skill.domain.Skill;
import java.util.List;
import lombok.Getter;

@Getter
public class SkillGetListResponse {

    private List<Skill> skills;

    public SkillGetListResponse(List<Skill> skills) {
        this.skills = skills;
    }
}
