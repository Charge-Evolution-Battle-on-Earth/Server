package com.project.game.match.dto;

import com.project.game.match.domain.MatchRoom;
import com.project.game.match.vo.MatchStatus;
import lombok.Getter;

@Getter
public class MatchRoomEnterResponse {

    private Long matchRoomId;
    private Long creatorId;
    private Long entrantId;
    private MatchStatus matchStatus;
    private Integer stakeGold;

    public MatchRoomEnterResponse(MatchRoom matchRoom) {
        this.matchRoomId = matchRoom.getMatchRoomId();
        this.creatorId = (matchRoom.getCreator() != null) ?matchRoom.getCreator().getCharacterId() : null;
        this.entrantId = (matchRoom.getEntrant() != null) ? matchRoom.getEntrant().getCharacterId() : null;
        this.matchStatus = matchRoom.getMatchStatus();
        this.stakeGold = matchRoom.getStakedGold();
    }
}
