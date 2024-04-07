package com.project.game.websocket.dto;

import lombok.Getter;

@Getter
public class ErrorMassageResponse {

    private String error;

    public ErrorMassageResponse(String error) {
        this.error = error;
    }
}
