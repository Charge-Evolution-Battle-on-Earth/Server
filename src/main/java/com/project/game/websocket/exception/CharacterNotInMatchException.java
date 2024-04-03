package com.project.game.websocket.exception;

import java.util.List;

public class CharacterNotInMatchException extends WebSocketException {

    public CharacterNotInMatchException(List<Long> playerIds) {
        super("Your Character is not in Match", playerIds);
    }

    public CharacterNotInMatchException(Long matchId, List<Long> playerIds) {
        super("[matchId]: " + matchId + "Your Character is not in Match", playerIds);
    }
}
