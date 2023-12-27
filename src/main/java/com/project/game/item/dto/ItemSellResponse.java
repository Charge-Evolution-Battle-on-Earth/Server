package com.project.game.item.dto;

import com.project.game.character.domain.Character;
import lombok.Getter;

@Getter
public class ItemSellResponse {

    Integer money;

    public ItemSellResponse(Character character) {
        this.money = character.getMoney();
    }
}
