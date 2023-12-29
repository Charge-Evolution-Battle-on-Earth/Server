package com.project.game.play.dto;

import lombok.Getter;

@Getter
public class PlayReadyResponse {

    String matchId;
    Boolean readyStatus1;    //true:준비완료, false:준비 미완료
    Boolean readyStatus2;    //true:준비완료, false:준비 미완료

    public PlayReadyResponse(String matchId, Boolean readyStatus1, Boolean readyStatus2) {
        this.matchId = matchId;
        this.readyStatus1 = readyStatus1;
        this.readyStatus2 = readyStatus2;
    }
}
