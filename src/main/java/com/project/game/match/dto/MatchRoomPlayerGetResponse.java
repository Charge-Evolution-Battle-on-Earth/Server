package com.project.game.match.dto;

import lombok.Getter;

@Getter
public class MatchRoomPlayerGetResponse {

    Long hostId;
    Long entrantId;

    //TODO 방에 참여 중인 사용자가 없을 경우 -1로 세팅함. 추후 수정하자
    public MatchRoomPlayerGetResponse(Long hostId, Long entrantId) {
        this.hostId = hostId == null ? -1 : hostId;
        this.entrantId = entrantId == null ? -1 : entrantId;
    }
}
