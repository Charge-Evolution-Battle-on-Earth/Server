package com.project.game.common.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.project.game.common.exception.ValueInvalidException;
import com.project.game.common.websocket.JsonProperty;

public class JsonUtil {

    public static <T> T jsonParseToDto(JsonObject jsonObject, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(jsonObject, clazz);
    }

    public static <T> T extractProperty(JsonObject jsonObject, JsonProperty key,
        Class<T> clazz) throws RuntimeException {
        JsonElement jsonElement = jsonObject.get(key.getProperty());

        if (jsonElement == null) {
            throw new ValueInvalidException();
        }

        if (clazz == String.class) {
            return clazz.cast(jsonElement.getAsString());
        } else if (clazz == Long.class) {
            return clazz.cast(jsonElement.getAsLong());
        } else {
            return clazz.cast(jsonElement.getAsJsonObject());
        }
    }

}
