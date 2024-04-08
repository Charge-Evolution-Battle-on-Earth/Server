package com.project.game.websocket.exception;

public class CharacterNotInMatchException extends WebSocketException {

    public CharacterNotInMatchException() {
        super("Your Character is not in Match");
    }

    public CharacterNotInMatchException(Long matchId) {
        super("Your Character is not in Match [matchId]: " + matchId);
    }
}
