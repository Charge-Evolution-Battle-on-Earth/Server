package com.project.game.play.exception;

import com.project.game.common.exception.ValueInvalidException;
import com.project.game.match.vo.MatchStatus;

public class MatchStatusInvalidException extends ValueInvalidException {

    public MatchStatusInvalidException() {
        super("Invalid MatchStatus");
    }

    public MatchStatusInvalidException(Long matchId) {
        super("[matchId] : "+matchId+" Invalid MatchStatus");
    }
}
