package com.project.game.item.service;

import com.project.game.character.domain.Character;
import com.project.game.character.domain.CharacterItem;
import com.project.game.character.domain.CharacterItemEquip;
import com.project.game.character.repository.CharacterItemEquipRepository;
import com.project.game.character.repository.CharacterItemRepository;
import com.project.game.character.repository.CharacterRepository;
import com.project.game.item.domain.Item;
import com.project.game.item.domain.ItemType;
import com.project.game.item.dto.ItemBuyRequest;
import com.project.game.item.dto.ItemBuyResponse;
import com.project.game.item.dto.ItemEquipRequest;
import com.project.game.item.dto.ItemEquipResponse;
import com.project.game.item.dto.ItemGetResponse;
import com.project.game.item.dto.ItemInvenGetResponse;
import com.project.game.item.dto.ItemSellRequest;
import com.project.game.item.dto.ItemSellResponse;
import com.project.game.item.dto.ItemUnEquipRequest;
import com.project.game.item.repository.ItemRepository;
import com.project.game.item.repository.ItemTypeRepository;
import com.project.game.item.service.usecase.ItemService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemTypeRepository itemTypeRepository;
    private final CharacterRepository characterRepository;
    private final CharacterItemRepository characterItemRepository;
    private final CharacterItemEquipRepository characterItemEquipRepository;

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

    @Override
    @Transactional
    public ItemBuyResponse buyItemOnShop(Long characterId, ItemBuyRequest itemBuyRequest) {
        Item item = itemRepository.findById(itemBuyRequest.getItemId()).orElseThrow();
        Character character = characterRepository.findById(characterId).orElseThrow();

        character.minusMoney(item.getCost());
        characterItemRepository.save(new CharacterItem(character, item));

        return new ItemBuyResponse(item.getItemId(), item.getItemNm(), character.getMoney());
    }

    @Override
    public List<ItemInvenGetResponse> getCharacterInventory(Long characterId, Long itemTypeId) {
        List<CharacterItem> characterItems = characterItemRepository.getInventoryList(itemTypeId);

        List<ItemInvenGetResponse> itemInvenGetResponses = characterItems.stream().map(
            characterItem -> new ItemInvenGetResponse(characterItem)
        ).collect(Collectors.toList());
        return itemInvenGetResponses;
    }

    @Override
    @Transactional
    public ItemEquipResponse equipItem(Long characterId, ItemEquipRequest itemEquipRequest) {
        Character character = characterRepository.findById(characterId).orElseThrow();
        ItemType itemType = itemTypeRepository.findById(itemEquipRequest.getItemTypeId()).orElseThrow();
        Optional<CharacterItemEquip> equippedItem = characterItemEquipRepository.findByCharacterCharacterIdAndItemTypeItemTypeId(characterId, itemEquipRequest.getItemTypeId());

        //장착 할 장비의 보유 여부 확인
        CharacterItem characterItem = characterItemRepository.findById(itemEquipRequest.getCharacterItemId()).orElseThrow();

        //아이템 타입 검증
        if(!itemType.getItemTypeId().equals(characterItem.getItem().getItemId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"잘못된 타입의 장비입니다.");
        }

        //장착 중이던 장비 해제
        if(equippedItem.isPresent()){
            characterItemEquipRepository.delete(equippedItem.get());
        }

        //장비 착용
        CharacterItemEquip recentEquippedItem = characterItemEquipRepository.save(
            new CharacterItemEquip(character, characterItem, itemType));

        return new ItemEquipResponse(recentEquippedItem);
    }

    @Override
    @Transactional
    public void unequipItem(Long characterId, ItemUnEquipRequest itemUnEquipRequest) {
        Character character = characterRepository.findById(characterId).orElseThrow();
        ItemType itemType = itemTypeRepository.findById(itemUnEquipRequest.getItemTypeId()).orElseThrow();
        Optional<CharacterItemEquip> equippedItem = characterItemEquipRepository.findByCharacterCharacterIdAndItemTypeItemTypeId(characterId, itemUnEquipRequest.getItemTypeId());

        //장착 할 장비의 보유 여부 확인
        CharacterItem characterItem = characterItemRepository.findById(itemUnEquipRequest.getCharacterItemId()).orElseThrow();

        //아이템 타입 검증
        if(!itemType.getItemTypeId().equals(characterItem.getItem().getItemId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"잘못된 타입의 장비입니다.");
        }

        //장착 중이던 장비 해제
        if(equippedItem.isPresent()){
            characterItemEquipRepository.delete(equippedItem.get());
        }
    }

    @Override
    @Transactional
    public ItemSellResponse sellItem(Long characterId, ItemSellRequest itemSellRequest) {
        Character character = characterRepository.findById(characterId).orElseThrow();
        ItemType itemType = itemTypeRepository.findById(itemSellRequest.getItemTypeId()).orElseThrow();
        Optional<CharacterItemEquip> equippedItem = characterItemEquipRepository.findByCharacterCharacterIdAndItemTypeItemTypeId(characterId, itemSellRequest.getItemTypeId());

        //판매 할 장비의 보유 여부 확인
        CharacterItem characterItem = characterItemRepository.findById(itemSellRequest.getCharacterItemId()).orElseThrow();

        //아이템 타입 검증
        if(!itemType.getItemTypeId().equals(characterItem.getItem().getItemId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"잘못된 타입의 장비입니다.");
        }

        //장착 중이던 장비 해제
        if(equippedItem.isPresent()){
            characterItemEquipRepository.delete(equippedItem.get());
        }

        //장비 판매
        character.plusMoney(characterItem.getItem().getCost());
        characterItemRepository.delete(characterItem);

        return new ItemSellResponse(character);
    }
}
