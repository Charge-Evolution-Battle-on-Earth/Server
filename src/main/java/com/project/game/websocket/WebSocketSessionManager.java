package com.project.game.websocket;

import com.project.game.websocket.exception.WebSocketSessionInvalidException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;


// TODO Thread 정보로 session을 식별하는 Thread Local를 통해서 Thread ID마다 Connection ID를 담아둬서 Connection ID를 활용하자.
@Component
public class WebSocketSessionManager {

    private Map<Long, WebSocketSession> webSocketSessionMap = new ConcurrentHashMap<>();

    public void addWebSocketSessionMap(Long key, WebSocketSession socketSession) {
        if (!validateSession(socketSession)) {
            throw new WebSocketSessionInvalidException();
        }
        this.webSocketSessionMap.put(key, socketSession);
    }

    public void removeWebSocketSessionMap(Long key) {
        this.webSocketSessionMap.remove(key);
    }

    public void sendMessage(String message, List<Long> keys) throws IOException {
        for (Long key : keys) {
            WebSocketSession socketSession = this.webSocketSessionMap.get(key);
            if (validateSession(socketSession)) {
                socketSession.sendMessage(new TextMessage(message));
            }
        }
    }

    public boolean validateSession(WebSocketSession socketSession) {
        return socketSession != null && socketSession.isOpen();
    }
}
