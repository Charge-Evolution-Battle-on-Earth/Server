package com.project.game.user.exception;

import com.project.game.common.exception.ValueInvalidException;

public class UserInvalidException extends ValueInvalidException {

    public UserInvalidException() {
        super("Invalid User");
    }

    public UserInvalidException(Long id) {
        super("Invalid User"+id);
    }
}
