package com.project.game.match.exception;

import com.project.game.common.exception.ValueInvalidException;

public class PlayerTypeInvalidException extends ValueInvalidException {

    public PlayerTypeInvalidException() {
        super("Player is not currently participating in the room");
    }

    public PlayerTypeInvalidException(Long id) {
        super("Player is not currently participating in the room: " + id);
    }
}
