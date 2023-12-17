package com.project.game.character.service.usecase;

import com.project.game.character.dto.CharacterInfoGetResponse;

public interface CharacterService {

    CharacterInfoGetResponse getCharacterInfo(Long characterId);
}
