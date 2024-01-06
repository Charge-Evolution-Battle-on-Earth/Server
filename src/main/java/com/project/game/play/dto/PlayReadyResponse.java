package com.project.game.play.dto;

import com.project.game.match.vo.MatchStatus;
import lombok.Getter;

@Getter
public class PlayReadyResponse {

    Boolean creatorReadyStatus;    //true:준비완료, false:준비 미완료
    Boolean entrantReadyStatus;    //true:준비완료, false:준비 미완료
    MatchStatus matchStatus;

    public PlayReadyResponse(Boolean creatorReadyStatus, Boolean entrantReadyStatus, MatchStatus matchStatus) {
        this.creatorReadyStatus = creatorReadyStatus;
        this.entrantReadyStatus = entrantReadyStatus;
        this.matchStatus = matchStatus;
    }
}
