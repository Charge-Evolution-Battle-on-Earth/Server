package com.project.game.item.controller;

import com.project.game.character.dto.CharacterInfoGetResponse;
import com.project.game.item.dto.ItemGetResponse;
import com.project.game.item.service.usecase.ItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    /*
     * 상점 ITEM 조회
     */
    @GetMapping("/{itemTypeId}/{levelId}/{jobId}")
    private ResponseEntity<List<ItemGetResponse>> findItemOnShop(@PathVariable Long itemTypeId,@PathVariable Long levelId, @PathVariable Long jobId) {
        List<ItemGetResponse> response = itemService.getItemOnShop(itemTypeId, levelId, jobId);
        return ResponseEntity.ok(response);
    }

    /*
     * 인벤토리 조회
     * TODO 추후 작업 예정
     */
    @GetMapping("/{itemTypeId}/{levelId}/{jobId}")
    private void findItemOnInventory() {
    }

}

