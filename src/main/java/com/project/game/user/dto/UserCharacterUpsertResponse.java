package com.project.game.user.dto;

import com.project.game.character.domain.Character;
import lombok.Getter;

@Getter
public class UserCharacterUpsertResponse {

    private Long characterId;
    private String accessToken;

    public UserCharacterUpsertResponse(Character character, String accessToken) {
        this.characterId = character.getCharacterId();
        this.accessToken = accessToken;
    }
}
