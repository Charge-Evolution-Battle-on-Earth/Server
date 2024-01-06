package com.project.game.match.exception;

import com.project.game.common.exception.ValueInvalidException;

public class LevelDifferenceInvalidException extends ValueInvalidException {

    public LevelDifferenceInvalidException() {
        super("Too many Level Difference");
    }

    public LevelDifferenceInvalidException(Integer difference) {
        super("Too many Level Difference: " + difference);
    }
}
