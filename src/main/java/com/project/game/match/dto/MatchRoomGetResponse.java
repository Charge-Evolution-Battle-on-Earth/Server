package com.project.game.match.dto;

import com.project.game.match.domain.MatchRoom;
import com.project.game.match.vo.MatchStatus;
import lombok.Getter;

@Getter
public class MatchRoomGetResponse {

    private Long matchRoomId;
    private Long creatorId;
    private Long entrantId;
    private MatchStatus matchStatus;
    private Integer stakeGold;

    public MatchRoomGetResponse(MatchRoom matchRoom) {
        this.matchRoomId = matchRoom.getMatchRoomId();
        this.creatorId = matchRoom.getCreator().getCharacterId();
        this.entrantId = matchRoom.getEntrant().getCharacterId();
        this.matchStatus = matchRoom.getMatchStatus();
        this.stakeGold = matchRoom.getStakedGold();
    }
}
