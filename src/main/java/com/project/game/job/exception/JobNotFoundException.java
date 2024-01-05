package com.project.game.job.exception;

public class JobNotFoundException extends RuntimeException{

    public JobNotFoundException() {
        super("Could not found Job");
    }

    public JobNotFoundException(Long id) {
        super("Could not found Job: " + id);
    }
}
