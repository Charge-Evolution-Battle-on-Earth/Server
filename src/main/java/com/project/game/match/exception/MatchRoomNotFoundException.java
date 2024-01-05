package com.project.game.match.exception;

import com.project.game.common.exception.EntityNotFoundException;

public class MatchRoomNotFoundException extends EntityNotFoundException {

    public MatchRoomNotFoundException() {
        super("Could not find MatchRoom");
    }

    public MatchRoomNotFoundException(Long id) {
        super("Could not find MatchRoom " + id);
    }

}
