package com.project.game.match.dto;


import com.project.game.character.domain.Character;
import com.project.game.match.vo.PlayerType;
import lombok.Getter;

@Getter
public class PlayQuitResponse {

    private PlayerType playerType;
    private String message;

    public PlayQuitResponse(Character character, PlayerType playerType) {
        this.message = character.getUser().getNickname() +  "님이 방을 떠났습니다.";
        this.playerType = playerType;
    }
}
