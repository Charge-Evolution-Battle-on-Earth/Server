package com.project.game.match.exception;

import com.project.game.common.exception.ValueInvalidException;

public class InvalidLevelDifferenceException extends ValueInvalidException {

    public InvalidLevelDifferenceException() {
        super("Too many Level Difference");
    }

    public InvalidLevelDifferenceException(Integer difference) {
        super("Too many Level Difference: " + difference);
    }
}
