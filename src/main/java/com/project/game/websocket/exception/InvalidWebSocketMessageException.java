package com.project.game.websocket.exception;

public class InvalidWebSocketMessageException extends WebSocketException {

    public InvalidWebSocketMessageException() {
        super("Invalid WebSocket Message");
    }

    public InvalidWebSocketMessageException(Long matchId) {
        super("Invalid WebSocket Message [matchId]: " + matchId);
    }
}
