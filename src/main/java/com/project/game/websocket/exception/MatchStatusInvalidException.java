package com.project.game.websocket.exception;

import java.util.List;

public class MatchStatusInvalidException extends WebSocketException {

    public MatchStatusInvalidException(List<Long> playerIds) {
        super("Invalid MatchStatus", playerIds);
    }

    public MatchStatusInvalidException(Long matchId, List<Long> playerIds) {
        super("Invalid MatchStatus [matchId]: " + matchId, playerIds);
    }
}
