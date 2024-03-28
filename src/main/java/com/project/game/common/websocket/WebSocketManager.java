package com.project.game.common.websocket;

import com.project.game.common.exception.ValueInvalidException;
import java.io.IOException;
import java.util.HashMap;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class WebSocketManager {

    //TODO Thread Safe 한 객체로 변경해야함, 메모리 해제(try-with-resources) 관련 이슈 확인하기
    private HashMap<Long, WebSocketSession> webSocketSessionMap = new HashMap<>();

    public void addWebSocketSessionMap(Long key, WebSocketSession socketSession) {
        if (!validateSession(socketSession)) {
            throw new ValueInvalidException();  //TODO Exception 정리
        }
        this.webSocketSessionMap.put(key, socketSession);
    }

    public void removeWebSocketSessionMap(Long key) {
        this.webSocketSessionMap.remove(key);
    }

    public void sendMessage(String message, Long... keys) throws IOException {
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
