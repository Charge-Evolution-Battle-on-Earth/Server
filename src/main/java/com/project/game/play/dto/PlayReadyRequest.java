package com.project.game.play.dto;

import lombok.Getter;

@Getter
public class PlayReadyRequest {

    String matchId;
    Boolean readyStatus;    //true:준비완료, false:준비 미완료
}
