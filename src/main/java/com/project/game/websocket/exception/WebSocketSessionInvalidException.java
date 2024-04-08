package com.project.game.websocket.exception;

public class WebSocketSessionInvalidException extends WebSocketException {

    public WebSocketSessionInvalidException() {
        super("WebSocketSession Invalid");
    }

    public WebSocketSessionInvalidException(Long matchId) {
        super("WebSocketSession Invalid [matchId]: " + matchId);
    }

}
