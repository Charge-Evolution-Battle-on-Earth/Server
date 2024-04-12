package com.project.game.websocket.dto;

import com.google.gson.JsonObject;

public record WebSocketMessageRequest(Long matchId, JsonObject request, String command) {

}
