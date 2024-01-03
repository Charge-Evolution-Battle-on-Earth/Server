package com.project.game.item.service;

import com.project.game.character.domain.Character;
import com.project.game.character.domain.CharacterItem;
import com.project.game.character.domain.CharacterItemEquip;
import com.project.game.character.exception.CharacterItemNotFoundException;
import com.project.game.character.exception.CharacterNotFoundException;
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
import com.project.game.item.exception.ItemNotFoundException;
import com.project.game.item.exception.ItemTypeInvalidException;
import com.project.game.item.exception.ItemTypeNotFoundException;
import com.project.game.item.repository.ItemRepository;
import com.project.game.item.repository.ItemTypeRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        Item item = itemRepository.findById(itemId).orElseThrow(()->new ItemNotFoundException(itemId));

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
        Item item = itemRepository.findById(itemBuyRequest.getItemId()).orElseThrow(()->new ItemNotFoundException(itemBuyRequest.getItemId()));
        Character character = characterRepository.findById(characterId).orElseThrow(()->new CharacterNotFoundException(characterId));

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

    private CharacterItem validateAndUnequipItem(Long characterItemId, ItemType itemType,
        Optional<CharacterItemEquip> equippedItem) {
        //장착 할 장비의 보유 여부 확인
        CharacterItem characterItem = characterItemRepository.findById(characterItemId).orElseThrow(()->new CharacterItemNotFoundException(characterItemId));

        //아이템 타입 검증
        if (!itemType.getItemTypeId().equals(characterItem.getItem().getItemId())) {
            throw new ItemTypeInvalidException(characterItem.getItem().getItemId());
        }

        //장착 중이던 장비 해제
        if (equippedItem.isPresent()) {
            characterItemEquipRepository.delete(equippedItem.get());
        }
        return characterItem;
    }

    @Override
    @Transactional
    public ItemEquipResponse equipItem(Long characterId, ItemEquipRequest itemEquipRequest) {
        Long itemTypeId = itemEquipRequest.getItemTypeId();
        Character character = characterRepository.findById(characterId).orElseThrow(()->new CharacterNotFoundException(characterId));
        ItemType itemType = itemTypeRepository.findById(itemTypeId).orElseThrow(()->new ItemTypeNotFoundException(itemTypeId));
        Optional<CharacterItemEquip> equippedItem = characterItemEquipRepository.findByCharacterCharacterIdAndItemTypeItemTypeId(characterId,itemTypeId);

        CharacterItem characterItem = validateAndUnequipItem(
            itemEquipRequest.getCharacterItemId(), itemType, equippedItem);

        //장비 착용
        CharacterItemEquip recentEquippedItem = characterItemEquipRepository.save(
            new CharacterItemEquip(character, characterItem, itemType));

        return new ItemEquipResponse(recentEquippedItem);
    }

    @Override
    @Transactional
    public void unequipItem(Long characterId, ItemUnEquipRequest itemUnEquipRequest) {
        Long itemTypeId = itemUnEquipRequest.getItemTypeId();
        ItemType itemType = itemTypeRepository.findById(itemTypeId).orElseThrow(()->new ItemTypeNotFoundException(itemTypeId));
        Optional<CharacterItemEquip> equippedItem = characterItemEquipRepository.findByCharacterCharacterIdAndItemTypeItemTypeId(characterId,itemTypeId);

        validateAndUnequipItem(itemUnEquipRequest.getCharacterItemId(), itemType, equippedItem);
    }

    @Override
    @Transactional
    public ItemSellResponse sellItem(Long characterId, ItemSellRequest itemSellRequest) {
        Long itemTypeId = itemSellRequest.getItemTypeId();
        Character character = characterRepository.findById(characterId).orElseThrow(()->new CharacterNotFoundException(characterId));
        ItemType itemType = itemTypeRepository.findById(itemTypeId).orElseThrow(()->new ItemTypeNotFoundException(itemTypeId));
        Optional<CharacterItemEquip> equippedItem = characterItemEquipRepository.findByCharacterCharacterIdAndItemTypeItemTypeId(characterId,itemTypeId);

        CharacterItem characterItem = validateAndUnequipItem(itemSellRequest.getCharacterItemId(), itemType, equippedItem);
        //장비 판매
        character.plusMoney(characterItem.getItem().getCost());
        characterItemRepository.delete(characterItem);

        return new ItemSellResponse(character);
    }
}
