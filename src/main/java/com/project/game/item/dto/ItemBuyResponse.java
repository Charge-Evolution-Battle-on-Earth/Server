package com.project.game.item.dto;

import lombok.Getter;

@Getter
public class ItemBuyResponse {

    Long itemId;
    String itemNm;
    Integer money;

    public ItemBuyResponse(Long itemId, String itemNm, Integer money) {
        this.itemId = itemId;
        this.itemNm = itemNm;
        this.money = money;
    }
}
