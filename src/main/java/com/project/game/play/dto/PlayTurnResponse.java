package com.project.game.play.dto;

import com.project.game.common.domain.Stat;
import com.project.game.match.vo.PlayerType;
import lombok.Getter;

@Getter
public class PlayTurnResponse {

    private Boolean isGameOver;
    private Stat hostStat;
    private Stat entrantStat;
    private PlayerType turnOwner;
    private String useSkillNm;
    private String message;

    public PlayTurnResponse(Boolean isGameOver, Stat hostStat, Stat entrantStat,
        String useSkillNm) {
        this.isGameOver = isGameOver;
        this.hostStat = hostStat;
        this.entrantStat = entrantStat;
        this.useSkillNm = useSkillNm;
        this.message = "게임이 종료되었습니다.";
    }

    public PlayTurnResponse(Boolean isGameOver, Stat hostStat, Stat entrantStat,
        PlayerType turnOwner, String useSkillNm) {
        this.isGameOver = isGameOver;
        this.hostStat = hostStat;
        this.entrantStat = entrantStat;
        this.turnOwner = turnOwner;
        this.useSkillNm = useSkillNm;
        this.message = useSkillNm + "발동!\n" + turnOwner.toString() + " 턴!";
    }
}
