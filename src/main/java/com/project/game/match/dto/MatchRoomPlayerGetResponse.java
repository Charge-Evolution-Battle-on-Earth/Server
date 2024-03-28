package com.project.game.match.dto;

import lombok.Getter;

@Getter
public class MatchRoomPlayerGetResponse {

    Long hostId;
    Long entrantId;

    public MatchRoomPlayerGetResponse(Long hostId, Long entrantId) {
        this.hostId = hostId;
        this.entrantId = entrantId;
    }
}
