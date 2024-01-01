package com.project.game.item.exception;

import com.project.game.common.exception.ValueInvalidException;

public class ItemTypeInvalidException extends ValueInvalidException {

    public ItemTypeInvalidException() {
        super("Invalid ItemType");
    }

    public ItemTypeInvalidException(Long id) {
        super("Invalid ItemType: " + id);
    }
}
