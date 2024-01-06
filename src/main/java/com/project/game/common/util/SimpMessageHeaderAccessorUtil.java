package com.project.game.common.util;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.socket.WebSocketSession;

public class SimpMessageHeaderAccessorUtil {

    public static Long extractCharacterIdFromAccessor(SimpMessageHeaderAccessor accessor){
        Object value = accessor.getSessionAttributes().get("characterId");
        if(value != null && value instanceof Long){
            return (Long) value;
        }else{
            throw new IllegalArgumentException();
        }
    }
}
