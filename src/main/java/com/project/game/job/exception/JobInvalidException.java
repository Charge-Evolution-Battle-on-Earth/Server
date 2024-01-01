package com.project.game.job.exception;

import com.project.game.common.exception.ValueInvalidException;

public class JobInvalidException extends ValueInvalidException {

    public JobInvalidException() {
        super("Invalid Job");
    }

    public JobInvalidException(Long id) {
        super("Invalid Job: "+ id);
    }
}
