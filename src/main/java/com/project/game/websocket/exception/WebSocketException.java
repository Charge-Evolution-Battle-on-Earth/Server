package com.project.game.websocket.exception;

import lombok.Getter;

/**
 * WebSocket 연결 중 발생하는 예외를 관리하는 최상위 클래스
 */
@Getter
public class WebSocketException extends RuntimeException {

    public WebSocketException() {
        super("WebSocket Exception Occurred");
    }

    public WebSocketException(String message) {
        super(message);
    }


}
