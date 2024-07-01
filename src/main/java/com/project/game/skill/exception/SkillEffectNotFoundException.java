package com.project.game.skill.exception;

public class SkillEffectNotFoundException extends RuntimeException {

    public SkillEffectNotFoundException() {
        super("Could not found SkillEffect");
    }

    public SkillEffectNotFoundException(Long id) {
        super("Could not found SkillEffect: " + id);
    }
}
