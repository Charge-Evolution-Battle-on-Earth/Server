package com.project.game.item.dto;

import lombok.Getter;

@Getter
public class ItemSellRequest {

    Long itemTypeId;
    Long characterItemId;   //캐릭터 아이템 ID
}
