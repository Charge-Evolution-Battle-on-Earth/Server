package com.project.game.play.dto;

import com.project.game.character.domain.Character;
import com.project.game.match.domain.MatchRoom;
import com.project.game.match.vo.PlayerType;
import lombok.Getter;

@Getter
public class PlaySurrenderResponse {

    private PlayerType winnerType;
    private PlayerType loserType;
    private Integer winnerGold;
    private Integer loserGold;
    private Integer winnerTotalGold;
    private Integer loserTotalGold;
    private Integer winnerTotalExp;
    private Integer loserTotalExp;

    public PlaySurrenderResponse(PlayerType winnerType, PlayerType loserType, Character winner, Character loser, MatchRoom matchRoom) {
        this.winnerType = winnerType;
        this.loserType = loserType;
        this.winnerGold = matchRoom.getWinnerGold(winner.getLevelId());
        this.loserGold = matchRoom.getLoserGold(loser.getLevelId());
        this.winnerTotalGold = winner.getMoney();
        this.loserTotalGold = loser.getMoney();
        this.winnerTotalExp = winner.getExp();
        this.loserTotalExp = loser.getExp();
        this.message = loser.getUser().getNickname()+" 항복\n"+"게임이 종료되었습니다.";
    }

    private String message;
}
