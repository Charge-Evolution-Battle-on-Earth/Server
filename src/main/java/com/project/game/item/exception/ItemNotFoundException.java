package com.project.game.item.exception;

import com.project.game.common.exception.EntityNotFoundException;

public class ItemNotFoundException extends EntityNotFoundException {

    public ItemNotFoundException() {
        super("Could not find Item ");
    }

    public ItemNotFoundException(Long id) {
        super("Could not find Item " + id);
    }
}