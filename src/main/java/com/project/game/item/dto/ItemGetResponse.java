package com.project.game.item.dto;

import com.project.game.common.domain.Stat;
import com.project.game.item.domain.Item;
import lombok.Getter;

@Getter
public class ItemGetResponse {

    private Long itemId;

    private Long levelId;

    private Long jobId;

    private Long itemTypeId;

    private String itemNm;

    private Integer cost;

    private Stat stat;

    private String description;

    public ItemGetResponse(Item item) {
        this.itemId = item.getItemId();
        this.levelId = item.getLevelId();
        this.jobId = item.getJob().getJobId();
        this.itemTypeId = item.getItemType().getItemTypeId();
        this.itemNm = item.getItemNm();
        this.cost = item.getCost();
        this.stat = item.getStat();
        this.description = item.getDescription();
    }
}
