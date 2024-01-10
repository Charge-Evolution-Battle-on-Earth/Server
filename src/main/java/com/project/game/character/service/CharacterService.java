package com.project.game.character.service;

import com.project.game.character.domain.Character;
import com.project.game.character.dto.CharacterInfoGetResponse;
import com.project.game.character.dto.CharacterSkillGetResponse;
import com.project.game.common.domain.Stat;
import java.util.List;

public interface CharacterService {

    CharacterInfoGetResponse getCharacterInfo(Long characterId);

    List<CharacterSkillGetResponse> getCharacterSkills(Long characterId);

    Stat getCharacterTotalStat(Character character);
}
