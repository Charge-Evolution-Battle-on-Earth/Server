package com.project.game.common.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.project.game.common.exception.ValueInvalidException;
import com.project.game.websocket.constant.WebSocketMessageProtocol;
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

    public <T> T fromJson(String payload, Class<T> jsonObjectClass) {
        return gson.fromJson(payload, jsonObjectClass);
    }

    public <T> T fromJson(JsonElement jsonElement, Class<T> jsonObjectClass) {
        return gson.fromJson(jsonElement, jsonObjectClass);
    }

    public String toJson(Object object) {
        return gson.toJson(object);
    }
}
