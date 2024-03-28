package com.project.game.common.util;

import com.project.game.common.exception.ValueInvalidException;
import org.springframework.web.socket.WebSocketSession;

public class WebSocketSessionUtil {

    public static <T> T extractCharacterId(WebSocketSession socketSession,
        Class<T> clazz)
        throws RuntimeException {
        Object attributeObject = socketSession.getAttributes().get("characterId");
        if (clazz.isInstance(attributeObject)) {
            return clazz.cast(attributeObject);
        } else {
            throw new ValueInvalidException();
        }
    }
}
