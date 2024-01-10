package com.project.game.match.exception;

import com.project.game.common.exception.ValueInvalidException;

public class PlayerTypeNotHostException extends ValueInvalidException {

    public PlayerTypeNotHostException() {
        super("Only Host can end game.");
    }
}
