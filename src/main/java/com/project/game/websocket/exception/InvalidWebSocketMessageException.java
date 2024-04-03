package com.project.game.websocket.exception;

import java.util.List;

public class InvalidWebSocketMessageException extends WebSocketException {

    public InvalidWebSocketMessageException(List<Long> playerIds) {
        super("Invalid WebSocket Message", playerIds);
    }

    public InvalidWebSocketMessageException(Long matchId, List<Long> playerIds) {
        super("Invalid WebSocket Message [matchId]: " + matchId, playerIds);
    }
}
