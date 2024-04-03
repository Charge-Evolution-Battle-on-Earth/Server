package com.project.game.websocket;

import com.project.game.websocket.exception.WebSocketSessionInvalidException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;


// TODO Thread 정보로 session을 식별 Thread Local ? Thread ID마다 Connection ID를 담아둬서 Connection ID를 활용하자.
@Component
public class WebSocketSessionManager {

    // TODO Thread Safe 한 객체로 변경해야함, 메모리 해제(try-with-resources) 관련 이슈 확인하기
    private Map<Long, WebSocketSession> webSocketSessionMap = new ConcurrentHashMap<>();

    public void addWebSocketSessionMap(Long key, WebSocketSession socketSession) {
        if (!validateSession(socketSession)) {
            throw new WebSocketSessionInvalidException(Collections.singletonList(key));
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
