package com.project.game.common.exception;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException() {
        super("Could not found entity");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
