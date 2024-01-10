package com.project.game.character.repository;

import com.project.game.character.domain.CharacterSkill;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterSkillRepository extends JpaRepository<CharacterSkill, Long> {

    List<CharacterSkill> findByCharacterCharacterId(Long characterId);

    Boolean existsByCharacterCharacterIdAndSkillSkillId(Long characterId, Long skillId);
}
