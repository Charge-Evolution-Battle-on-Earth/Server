package com.project.game.character.exception;

import com.project.game.common.exception.EntityNotFoundException;

public class CharacterNotFoundException extends EntityNotFoundException {

    public CharacterNotFoundException() {
        super("Could not find Character");
    }

    public CharacterNotFoundException(Long id) {
        super("Could not find Character " + id);
    }

}
