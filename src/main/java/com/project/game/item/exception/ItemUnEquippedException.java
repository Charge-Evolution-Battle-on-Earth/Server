package com.project.game.item.exception;

import com.project.game.common.exception.ValueInvalidException;

public class ItemUnEquippedException extends ValueInvalidException {

    public ItemUnEquippedException() {
        super("Item is Not Equipped");
    }

    public ItemUnEquippedException(Long id) {
        super("Item is Not Equipped " + id);
    }
}
