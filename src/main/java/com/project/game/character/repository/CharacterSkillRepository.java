package com.project.game.character.repository;

import com.project.game.character.domain.CharacterSkill;
import com.project.game.character.dto.CharacterSkillGetResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterSkillRepository extends JpaRepository<CharacterSkill, Long> {

    List<CharacterSkill> findByCharacterCharacterId(Long characterId);
}
