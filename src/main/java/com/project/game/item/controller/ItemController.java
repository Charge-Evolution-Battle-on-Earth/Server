package com.project.game.item.controller;

import com.project.game.item.dto.ItemBuyRequest;
import com.project.game.item.dto.ItemBuyResponse;
import com.project.game.item.dto.ItemEquipRequest;
import com.project.game.item.dto.ItemEquipResponse;
import com.project.game.item.dto.ItemGetResponse;
import com.project.game.item.dto.ItemInvenGetResponse;
import com.project.game.item.dto.ItemSellRequest;
import com.project.game.item.dto.ItemSellResponse;
import com.project.game.item.dto.ItemUnEquipRequest;
import com.project.game.item.service.ItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{itemId}")
    private ResponseEntity<ItemGetResponse> findItem(@PathVariable Long itemId) {
        ItemGetResponse response = itemService.getItem(itemId);
        return ResponseEntity.ok(response);
    }

    /**
     * 조건 별 아이템 리스트 조회
     * @param itemTypeId 
     * @param levelId
     * @param jobId
     * @return
     */
    @GetMapping("/{itemTypeId}/{levelId}/{jobId}")
    private ResponseEntity<List<ItemGetResponse>> findItemOnShop(@PathVariable Long itemTypeId,@PathVariable Long levelId, @PathVariable Long jobId) {
        List<ItemGetResponse> response = itemService.getItemOnShop(itemTypeId, levelId, jobId);
        return ResponseEntity.ok(response);
    }

    /**
     * 아이템 구매
     * @param authentication 
     * @param itemBuyRequest
     * @return
     */
    @PostMapping("/buy")
    private ResponseEntity<ItemBuyResponse> buyItemOnShop(Authentication authentication,  @RequestBody ItemBuyRequest itemBuyRequest) {
        Long characterId = Long.valueOf(authentication.getName());

        ItemBuyResponse response = itemService.buyItemOnShop(characterId, itemBuyRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * 캐릭터 인벤토리 조회
     * @param authentication
     * @param itemTypeId
     * @return
     */
    @GetMapping("/inven/{itemTypeId}")
    private ResponseEntity<List<ItemInvenGetResponse>> getCharacterInventory(Authentication authentication, @PathVariable Long itemTypeId) {
        Long characterId = Long.valueOf(authentication.getName());

        List<ItemInvenGetResponse> response = itemService.getCharacterInventory(characterId, itemTypeId);
        return ResponseEntity.ok(response);
    }

    /**
     * 아이템 장착
     * @param authentication 
     * @param itemEquipRequest
     * @return
     */
    @PostMapping("/equip")
    private ResponseEntity<ItemEquipResponse> equipItem(Authentication authentication, @RequestBody ItemEquipRequest itemEquipRequest) {
        Long characterId = Long.valueOf(authentication.getName());

        ItemEquipResponse response = itemService.equipItem(characterId, itemEquipRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * 아이템 장착 해제
     * @param authentication 
     * @param itemUnEquipRequest
     * @return
     */
    @PostMapping("/unequip")
    private ResponseEntity<Void> unequipItem(Authentication authentication, @RequestBody ItemUnEquipRequest itemUnEquipRequest) {
        Long characterId = Long.valueOf(authentication.getName());

        itemService.unequipItem(characterId, itemUnEquipRequest);
        return ResponseEntity.noContent().build();
    }

    /**
     * 아이템 판매
     * @param authentication 
     * @param itemSellRequest
     * @return
     */
    @PostMapping("/sell")
    private ResponseEntity<ItemSellResponse> sellItem(Authentication authentication, @RequestBody ItemSellRequest itemSellRequest) {
        Long characterId = Long.valueOf(authentication.getName());

        ItemSellResponse response = itemService.sellItem(characterId, itemSellRequest);
        return ResponseEntity.ok(response);
    }
}

