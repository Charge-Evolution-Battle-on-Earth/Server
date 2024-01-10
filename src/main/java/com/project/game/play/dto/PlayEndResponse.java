package com.project.game.play.dto;

import com.project.game.character.domain.Character;
import com.project.game.match.vo.PlayerType;
import lombok.Getter;

@Getter
public class PlayEndResponse {

    private PlayerType winnerType;
    private PlayerType loserType;
    private Integer winnerGold;
    private Integer loserGold;
    private Integer winnerExp;
    private Integer loserExp;
    private Integer winnerTotalGold;
    private Integer loserTotalGold;
    private Integer winnerTotalExp;
    private Integer loserTotalExp;
    private String message;

    public PlayEndResponse(PlayerType winnerType, PlayerType loserType, Character winner, Character loser, Integer winnerGold, Integer loserGold, Integer winnerExp, Integer loserExp) {
        this.winnerType = winnerType;
        this.loserType = loserType;
        this.winnerGold = winnerGold;
        this.loserGold = loserGold;
        this.winnerExp = winnerExp;
        this.loserExp = loserExp;
        this.winnerTotalGold = winner.getMoney();
        this.loserTotalGold = loser.getMoney();
        this.winnerTotalExp = winner.getExp();
        this.loserTotalExp = loser.getExp();
        this.message = winner.getUser().getNickname()+" 승리!\n"+"게임이 종료되었습니다.";
    }
}
