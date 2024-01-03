package com.project.game.character.service;

import com.project.game.character.dto.CharacterInfoGetResponse;
import com.project.game.character.dto.CharacterSkillGetResponse;
import java.util.List;

public interface CharacterService {

    CharacterInfoGetResponse getCharacterInfo(Long characterId);

    List<CharacterSkillGetResponse> getCharacterSkills(Long characterId);
}
