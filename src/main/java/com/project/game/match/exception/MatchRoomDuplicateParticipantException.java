package com.project.game.match.exception;

import com.project.game.common.exception.ValueInvalidException;

public class MatchRoomDuplicateParticipantException extends ValueInvalidException {

    public MatchRoomDuplicateParticipantException() {
        super("Duplicate Participant Exception");
    }

    public MatchRoomDuplicateParticipantException(Long characterId) {
        super("Duplicate Participant Exception: " + characterId);
    }
}
