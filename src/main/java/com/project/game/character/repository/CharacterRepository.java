package com.project.game.character.repository;

import com.project.game.character.domain.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CharacterRepository extends JpaRepository<Character,Long> {

}
