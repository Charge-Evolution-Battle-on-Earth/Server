package com.project.game.websocket.exception;

import java.util.List;
import lombok.Getter;

/**
 * WebSocket 연결 중 발생하는 예외를 관리하는 최상위 클래스
 */
@Getter
public class WebSocketException extends RuntimeException {

    private List<Long> playerIds;

    public WebSocketException(List<Long> playerIds) {
        super("WebSocket Exception Occurred");
        this.playerIds = playerIds;
    }

    public WebSocketException(String message, List<Long> playerIds) {
        super(message);
        this.playerIds = playerIds;
    }


}
