package com.project.game.character.exception;

import com.project.game.common.exception.EntityNotFoundException;

public class CharacterSkillNotFoundException extends EntityNotFoundException {

    public CharacterSkillNotFoundException() {
        super("Could not find CharacterSkill");
    }

}