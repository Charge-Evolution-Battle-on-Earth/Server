package com.project.game.character.exception;

import com.project.game.common.exception.ValueInvalidException;

public class CostInvalidException extends ValueInvalidException {

    public CostInvalidException() {
        super("Invalid cost value");
    }

    public CostInvalidException(Integer cost) {
        super("Invalid cost value: " + cost);
    }
}
