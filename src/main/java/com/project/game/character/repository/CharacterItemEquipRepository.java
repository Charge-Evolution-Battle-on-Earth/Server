package com.project.game.character.repository;

import com.project.game.character.domain.CharacterItemEquip;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterItemEquipRepository extends JpaRepository<CharacterItemEquip, Long> {

    Optional<CharacterItemEquip> findByCharacterCharacterIdAndItemTypeItemTypeId(Long characterId, Long itemTypeId);

    List<CharacterItemEquip> findByCharacterCharacterId(Long characterId);
}
