package com.project.game.websocket.exception;

import java.util.List;

public class WebSocketSessionInvalidException extends WebSocketException {

    public WebSocketSessionInvalidException(List<Long> playerIds) {
        super("WebSocketSession Invalid", playerIds);
    }

    public WebSocketSessionInvalidException(Long matchId, List<Long> playerIds) {
        super("[matchId]: " + matchId + "WebSocketSession Invalid", playerIds);
    }

}
