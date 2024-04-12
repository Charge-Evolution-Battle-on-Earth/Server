package com.project.game.common.util;

import static com.project.game.websocket.constant.WebSocketMessageProtocol.COMMAND;
import static com.project.game.websocket.constant.WebSocketMessageProtocol.MATCH_ID;
import static com.project.game.websocket.constant.WebSocketMessageProtocol.REQUEST;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.project.game.common.exception.ValueInvalidException;
import com.project.game.websocket.constant.WebSocketMessageProtocol;
import com.project.game.websocket.dto.WebSocketMessageRequest;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JsonUtil {

    private final Gson gson = new Gson();

    public <T> T extractProperty(JsonObject jsonObject, WebSocketMessageProtocol key,
        Class<T> clazz) throws RuntimeException {
        JsonElement jsonElement = jsonObject.get(key.getProperty());

        if (jsonElement == null) {
            throw new ValueInvalidException();
        }
        return gson.fromJson(jsonElement, clazz);
    }

    public WebSocketMessageRequest parseWebSocketMessage(String payload) {
        JsonObject jsonObject = gson.fromJson(payload, JsonObject.class);
        Long matchId = extractProperty(jsonObject, MATCH_ID, Long.class);
        JsonObject request = extractProperty(jsonObject, REQUEST, JsonObject.class);
        String command = extractProperty(jsonObject, COMMAND, String.class);

        return new WebSocketMessageRequest(matchId, request, command);
    }

    public <T> T fromJson(JsonElement jsonElement, Class<T> jsonObjectClass) {
        return gson.fromJson(jsonElement, jsonObjectClass);
    }

    public String toJson(Object object) {
        return gson.toJson(object);
    }
}
