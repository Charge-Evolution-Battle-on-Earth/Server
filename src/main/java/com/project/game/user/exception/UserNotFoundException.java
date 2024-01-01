package com.project.game.user.exception;

import com.project.game.common.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException() {
        super("Could not found user");
    }

    public UserNotFoundException(Long id) {
        super("Could not found user"+id);
    }
}
