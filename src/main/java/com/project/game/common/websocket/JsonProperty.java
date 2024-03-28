package com.project.game.common.websocket;

import lombok.Getter;

@Getter
public enum JsonProperty {

    COMMAND("command"),
    MATCH_ID("matchId"),
    REQUEST("request"),
    NONE("none");


    private final String property;

    JsonProperty(String property) {
        this.property = property;
    }

}
