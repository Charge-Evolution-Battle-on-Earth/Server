package com.project.game.item.dto;

import lombok.Getter;

@Getter
public class ItemEquipRequest {

    Long itemTypeId;
    Long characterItemId;   //캐릭터 아이템 ID
}
