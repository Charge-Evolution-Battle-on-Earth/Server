package com.project.game.nation.exception;

public class NationNotFoundException extends RuntimeException{

    public NationNotFoundException() {
        super("Could not found Nation");
    }

    public NationNotFoundException(Long id) {
        super("Could not found Nation: " + id);
    }
}
