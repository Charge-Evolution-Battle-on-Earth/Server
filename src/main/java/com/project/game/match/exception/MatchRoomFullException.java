package com.project.game.match.exception;

import com.project.game.common.exception.AccessDeniedException;

public class MatchRoomFullException extends AccessDeniedException {

    public MatchRoomFullException() {
        super("MatchRoom Full");
    }

    public MatchRoomFullException(Long id) {
        super("MatchRoom Full: " + id);
    }
}
