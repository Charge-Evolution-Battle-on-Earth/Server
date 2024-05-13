package com.project.game.play.dto;

import lombok.Getter;

@Getter
public class PlayGreetingResponse {

    String greetingMessage;

    Long jobId;

    String jobNm;

    public PlayGreetingResponse(String characterNickname, Long jobId, String jobNm) {
        this.greetingMessage = "[" + characterNickname + "님이 입장하였습니다.]";
        this.jobId = jobId;
        this.jobNm = jobNm;
    }
}
