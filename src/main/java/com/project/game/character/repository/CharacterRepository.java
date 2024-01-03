package com.project.game.character.repository;

import com.project.game.character.domain.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character,Long> {

    Character findByUserUserId(Long userId);
}
