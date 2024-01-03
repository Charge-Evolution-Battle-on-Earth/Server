package com.project.game.item.service;

import com.project.game.item.dto.ItemBuyRequest;
import com.project.game.item.dto.ItemBuyResponse;
import com.project.game.item.dto.ItemEquipRequest;
import com.project.game.item.dto.ItemEquipResponse;
import com.project.game.item.dto.ItemGetResponse;
import com.project.game.item.dto.ItemInvenGetResponse;
import com.project.game.item.dto.ItemSellRequest;
import com.project.game.item.dto.ItemSellResponse;
import com.project.game.item.dto.ItemUnEquipRequest;
import java.util.List;

public interface ItemService {

    ItemGetResponse getItem(Long itemId);

    List<ItemGetResponse> getItemOnShop(Long itemTypeId, Long levelId, Long jobId);

    ItemBuyResponse buyItemOnShop(Long characterId, ItemBuyRequest itemBuyRequest);

    List<ItemInvenGetResponse> getCharacterInventory(Long characterId, Long itemTypeId);

    ItemEquipResponse equipItem(Long characterId, ItemEquipRequest itemEquipRequest);

    void unequipItem(Long characterId, ItemUnEquipRequest itemUnEquipRequest);

    ItemSellResponse sellItem(Long characterId, ItemSellRequest itemSellRequest);
}
