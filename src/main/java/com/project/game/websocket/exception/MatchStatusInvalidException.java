package com.project.game.websocket.exception;

public class MatchStatusInvalidException extends WebSocketException {

    public MatchStatusInvalidException() {
        super("Invalid MatchStatus");
    }

    public MatchStatusInvalidException(Long matchId) {
        super("Invalid MatchStatus [matchId]: " + matchId);
    }
}
