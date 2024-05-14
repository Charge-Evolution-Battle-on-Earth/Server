package com.project.game.match.dto;

import com.project.game.match.domain.MatchRoom;
import com.project.game.match.vo.MatchStatus;
import lombok.Getter;

@Getter
public class MatchRoomGetResponse {

    private Long matchRoomId;
    private Long hostId;
    private Long entrantId;
    private String hostNickname;
    private String entrantNickname;
    private MatchStatus matchStatus;
    private Integer stakeGold;

    public MatchRoomGetResponse(MatchRoom matchRoom) {
        this.matchRoomId = matchRoom.getMatchRoomId();
        this.hostId = (matchRoom.getHost() != null) ? matchRoom.getHost().getCharacterId() : null;
        this.entrantId =
            (matchRoom.getEntrant() != null) ? matchRoom.getEntrant().getCharacterId() : null;
        this.hostNickname =
            (matchRoom.getHost() != null && matchRoom.getHost().getUser() != null)
                ? matchRoom.getHost().getUser().getNickname() : null;
        this.entrantNickname =
            (matchRoom.getEntrant() != null && matchRoom.getEntrant().getUser() != null)
                ? matchRoom.getEntrant().getUser().getNickname() : null;
        this.matchStatus = matchRoom.getMatchStatus();
        this.stakeGold = matchRoom.getStakedGold();
    }
}
