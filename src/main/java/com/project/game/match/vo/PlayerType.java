package com.project.game.match.vo;

/**
 * MatchRoom 내 매치 참여자의 상태 표현
 */
public enum PlayerType {

    HOST,
    ENTRANT,
    NONE;

    public static PlayerType togglePlayerType(PlayerType type){
        if(type == HOST) return ENTRANT;
        else if(type == ENTRANT) return HOST;
        else return NONE;
    }
}
