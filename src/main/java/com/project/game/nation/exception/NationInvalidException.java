package com.project.game.nation.exception;

import com.project.game.common.exception.ValueInvalidException;

public class NationInvalidException extends ValueInvalidException {

    public NationInvalidException() {
        super("Invalid Nation");
    }

    public NationInvalidException(Long id) {
        super("Invalid Nation"+id);
    }
}
