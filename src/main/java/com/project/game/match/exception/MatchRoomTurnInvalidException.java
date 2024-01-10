package com.project.game.match.exception;

import com.project.game.common.exception.ValueInvalidException;

public class MatchRoomTurnInvalidException extends ValueInvalidException {

    public MatchRoomTurnInvalidException() {
        super("Invalid Turn request.");
    }

    public MatchRoomTurnInvalidException(Long id) {
        super("Invalid Turn request.: " + id);
    }
}

