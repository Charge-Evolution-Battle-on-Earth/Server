package com.project.game.skill.dto;

import com.project.game.common.exception.EntityNotFoundException;

public class SkillNotFoundException extends EntityNotFoundException {

    public SkillNotFoundException() {
        super("Could not find Skill");
    }

    public SkillNotFoundException(Long id) {
        super("Could not find Skill: " + id);
    }

}