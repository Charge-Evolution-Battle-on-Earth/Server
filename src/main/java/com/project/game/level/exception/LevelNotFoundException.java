package com.project.game.level.exception;

import com.project.game.common.exception.EntityNotFoundException;

public class LevelNotFoundException extends EntityNotFoundException {

    public LevelNotFoundException(Integer id) {
        super("Could not find Level " + id);
    }
}
