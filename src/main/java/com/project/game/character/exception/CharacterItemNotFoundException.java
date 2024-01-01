package com.project.game.character.exception;

import com.project.game.common.exception.EntityNotFoundException;

public class CharacterItemNotFoundException extends EntityNotFoundException {

    public CharacterItemNotFoundException() {
        super("Could not find CharacterItem");
    }

    public CharacterItemNotFoundException(Long id) {
        super("Could not find CharacterItem: " + id);
    }
}
