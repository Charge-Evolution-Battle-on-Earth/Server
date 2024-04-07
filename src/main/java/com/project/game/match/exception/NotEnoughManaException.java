package com.project.game.match.exception;

import com.project.game.common.exception.ValueInvalidException;

public class NotEnoughManaException extends ValueInvalidException {

    public NotEnoughManaException() {
        super("Not Enough Mana");
    }
}
