package com.project.game.match.vo;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class MatchPlayer {

    Long hostId;
    Long entrantId;

    //TODO 방에 참여 중인 사용자가 없을 경우 -1로 세팅함. 추후 수정하자
    public MatchPlayer(Long hostId, Long entrantId) {
        this.hostId = hostId == null ? -1 : hostId;
        this.entrantId = entrantId == null ? -1 : entrantId;
    }

    public List<Long> toList() {
        ArrayList<Long> playerIds = new ArrayList<>();
        if (hostId != -1) {
            playerIds.add(hostId);
        }
        if (entrantId != -1) {
            playerIds.add(entrantId);
        }
        return playerIds;
    }

    public boolean isContainsPlayer(Long characterId) {
        return hostId.equals(characterId) || entrantId.equals(characterId);
    }

}
