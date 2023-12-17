package com.project.game.character.service;

import com.project.game.character.domain.Character;
import com.project.game.character.dto.CharacterInfoGetResponse;
import com.project.game.character.repository.CharacterRepository;
import com.project.game.character.service.usecase.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;

    @Override
    public CharacterInfoGetResponse getCharacterInfo(Long characterId) {
        Character character = characterRepository.findById(characterId).orElseThrow();

        CharacterInfoGetResponse responseDTO = new CharacterInfoGetResponse(character);
        return responseDTO;
    }
}
