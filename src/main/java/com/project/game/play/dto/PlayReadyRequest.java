package com.project.game.play.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayReadyRequest {

    Boolean selfReadyStatus;    //true:준비완료, false:준비 미완료
    Boolean opponentReadyStatus;    //true:준비완료, false:준비 미완료

    public Boolean getToggleSelfReadyStatus() {
        return !this.selfReadyStatus;
    }
}
