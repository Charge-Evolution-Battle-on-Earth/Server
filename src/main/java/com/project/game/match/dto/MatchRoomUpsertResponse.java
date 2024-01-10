package com.project.game.match.dto;

import com.project.game.match.domain.MatchRoom;
import com.project.game.match.vo.MatchStatus;
import lombok.Getter;

@Getter
public class MatchRoomUpsertResponse {

    private Long matchRoomId;
    private Long hostId;
    private Long entrantId;
    private MatchStatus matchStatus;
    private Integer stakeGold;

    public MatchRoomUpsertResponse(MatchRoom matchRoom) {
        this.matchRoomId = matchRoom.getMatchRoomId();
        this.hostId = (matchRoom.getHost() != null) ?matchRoom.getHost().getCharacterId() : null;
        this.entrantId = (matchRoom.getEntrant() != null) ? matchRoom.getEntrant().getCharacterId() : null;
        this.matchStatus = matchRoom.getMatchStatus();
        this.stakeGold = matchRoom.getStakedGold();
    }
}
