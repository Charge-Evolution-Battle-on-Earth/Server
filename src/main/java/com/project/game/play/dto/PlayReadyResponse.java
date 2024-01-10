package com.project.game.play.dto;

import com.project.game.match.vo.MatchStatus;
import lombok.Getter;

@Getter
public class PlayReadyResponse {

    Boolean hostReadyStatus;    //true:준비완료, false:준비 미완료
    Boolean entrantReadyStatus;    //true:준비완료, false:준비 미완료
    MatchStatus matchStatus;

    public PlayReadyResponse(Boolean hostReadyStatus, Boolean entrantReadyStatus, MatchStatus matchStatus) {
        this.hostReadyStatus = hostReadyStatus;
        this.entrantReadyStatus = entrantReadyStatus;
        this.matchStatus = matchStatus;
    }
}
