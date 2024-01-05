package com.project.game.match.exception;

/*
 * MatchRoom 플레이어가 모두 채워져있는 경우 Exception 발생
 */
public class MatchRoomFullException extends RuntimeException{

    public MatchRoomFullException() {
        super("MatchRoom Full");
    }

    public MatchRoomFullException(Long id) {
        super("MatchRoom Full: " + id);
    }
}
