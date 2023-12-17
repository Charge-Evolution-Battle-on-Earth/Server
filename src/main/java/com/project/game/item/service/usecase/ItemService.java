package com.project.game.item.service.usecase;

import com.project.game.item.dto.ItemGetResponse;
import java.util.List;

public interface ItemService {

    ItemGetResponse getItem(Long itemId);

    List<ItemGetResponse> getItemOnShop(Long itemTypeId, Long levelId, Long jobId);
}
