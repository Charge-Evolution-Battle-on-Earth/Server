package com.project.game.common.exception;

/*
 * MatchRoom 플레이어가 모두 채워져있는 경우 Exception 발생
 */
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException() {
        super("Access Denied");
    }

    public AccessDeniedException(String message) {
        super(message);
    }
}
