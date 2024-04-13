package com.project.game.character.repository;

import com.project.game.character.domain.CharacterItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CharacterItemRepository extends JpaRepository<CharacterItem, Long> {

    @Query(value = "SELECT ci.* "
        + "FROM character_item ci "
        + "JOIN items i on ci.item_id = i.item_id "
        + "JOIN item_type it on i.item_type_id = it.item_type_id "
        + "WHERE it.item_type_id = :itemTypeId"
        + "AND ci.character_id = :characterId"
        , nativeQuery = true)
    List<CharacterItem> getInventoryList(@Param("characterId") Long characterId,
        @Param("itemTypeId") Long itemTypeId);
}
