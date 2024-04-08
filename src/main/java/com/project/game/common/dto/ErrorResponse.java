package com.project.game.common.dto;

import lombok.Getter;

@Getter
public class ErrorResponse {

    String type;
    String message;

    public ErrorResponse(Throwable e) {
        this.type = e.getClass().getSimpleName();
        this.message = e.getMessage();
    }
}
