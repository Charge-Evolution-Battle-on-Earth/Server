package com.project.game.item.dto;

import com.project.game.common.domain.Stat;

public record ItemUpsertRequest(Long itemId, Integer cost, Stat stat) {

}
