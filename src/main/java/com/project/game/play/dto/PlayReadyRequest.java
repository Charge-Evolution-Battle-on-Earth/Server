package com.project.game.play.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayReadyRequest {

    Boolean hostReadyStatus;    //true:준비완료, false:준비 미완료
    Boolean entrantReadyStatus;    //true:준비완료, false:준비 미완료
}
