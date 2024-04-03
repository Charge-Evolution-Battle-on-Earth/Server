package com.project.game.websocket.constant;

import lombok.Getter;

@Getter
public enum WebSocketMessageProtocol {

    COMMAND("command"),
    MATCH_ID("matchId"),
    REQUEST("request"),
    NONE("none");

    private final String property;

    WebSocketMessageProtocol(String property) {
        this.property = property;
    }

}
