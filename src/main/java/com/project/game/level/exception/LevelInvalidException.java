package com.project.game.level.exception;

import com.project.game.common.exception.ValueInvalidException;

public class LevelInvalidException extends ValueInvalidException {

    public LevelInvalidException() {
        super("Invalid Level");
    }

    public LevelInvalidException(Integer id) {
        super("Invalid Level" + id);
    }
}
