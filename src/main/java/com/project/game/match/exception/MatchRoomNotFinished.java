package com.project.game.match.exception;

import com.project.game.common.exception.ValueInvalidException;
import lombok.Getter;

@Getter
public class MatchRoomNotFinished extends ValueInvalidException {

    public MatchRoomNotFinished() {
        super("MatchRoom Not Finsihed");
    }
}
