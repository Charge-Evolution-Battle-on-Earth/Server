package com.project.game.match.service;

import com.project.game.match.dto.MatchRoomEnterRequest;
import com.project.game.match.dto.MatchRoomEnterResponse;
import com.project.game.match.dto.MatchRoomGetResponse;
import com.project.game.match.dto.MatchRoomUpsertResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface MatchService {

    Slice<MatchRoomGetResponse> findMatchRoomList(Pageable pageable);

    MatchRoomUpsertResponse saveMatchRoom(Long characterId);

    MatchRoomEnterResponse matchRoomEnter(Long characterId, MatchRoomEnterRequest matchRoomEnterRequest);
}
