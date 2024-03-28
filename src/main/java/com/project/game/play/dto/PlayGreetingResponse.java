package com.project.game.play.dto;

import lombok.Getter;

@Getter
public class PlayGreetingResponse {

    String greetingMessage;

    public PlayGreetingResponse(String greetingMessage) {
        this.greetingMessage = greetingMessage;
    }
}
