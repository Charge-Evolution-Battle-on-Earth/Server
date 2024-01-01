package com.project.game.item.exception;

import com.project.game.common.exception.EntityNotFoundException;

public class ItemTypeNotFoundException extends EntityNotFoundException {


    public ItemTypeNotFoundException() {
        super("Could not find ItemType");
    }

    public ItemTypeNotFoundException(Long id) {
        super("Could not find Item type " + id);
    }
}