package com.project.game.item.service;

import com.project.game.item.domain.Item;
import com.project.game.item.dto.ItemGetResponse;
import com.project.game.item.repository.ItemRepository;
import com.project.game.item.service.usecase.ItemService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public ItemGetResponse getItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow();

        ItemGetResponse responseDTO = new ItemGetResponse(item);
        return responseDTO;
    }

    @Override
    public List<ItemGetResponse> getItemOnShop(Long itemTypeId, Long levelId, Long jobId) {
        List<Item> items = itemRepository.findByItemTypeItemTypeIdAndLevelIdLessThanAndJobJobId(itemTypeId, levelId, jobId);
        List<ItemGetResponse> itemResponseList = items.stream().map(
            item -> new ItemGetResponse(item)
        ).collect(Collectors.toList());
        return itemResponseList;
    }
}
