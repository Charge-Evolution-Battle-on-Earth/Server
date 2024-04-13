package com.project.game.websocket;

import com.project.game.websocket.exception.WebSocketSessionInvalidException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class WebSocketSessionManager {

    private Map<Long, WebSocketSession> webSocketSessionMap = new ConcurrentHashMap<>();

    public synchronized void addWebSocketSessionMap(Long key, WebSocketSession socketSession) {
        if (!validateSession(socketSession)) {
            throw new WebSocketSessionInvalidException();
        }
        this.webSocketSessionMap.put(key, socketSession);
    }

    public synchronized void removeWebSocketSessionMap(Long key) {
        this.webSocketSessionMap.remove(key);
    }

    public synchronized void sendMessage(String message, List<Long> keys) throws IOException {
        for (Long key : keys) {
            WebSocketSession socketSession = this.webSocketSessionMap.get(key);
            if (validateSession(socketSession)) {
                socketSession.sendMessage(new TextMessage(message));
            }
        }
    }

    public synchronized boolean validateSession(WebSocketSession socketSession) {
        return socketSession != null && socketSession.isOpen();
    }

    public boolean isContainsKey(Long key) {
        return webSocketSessionMap.containsKey(key);
    }
}
