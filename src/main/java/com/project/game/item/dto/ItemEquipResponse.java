package com.project.game.item.dto;

import com.project.game.character.domain.CharacterItemEquip;
import lombok.Getter;

@Getter
public class ItemEquipResponse {

   Long characterItemId;

    public ItemEquipResponse(CharacterItemEquip characterItemEquip) {
        this.characterItemId = characterItemEquip.getCharacterItem().getCharacterItemId();
    }
}
