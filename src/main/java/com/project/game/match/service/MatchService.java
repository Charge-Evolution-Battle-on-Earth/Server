package com.project.game.match.service;

import com.project.game.match.dto.MatchRoomEnterRequest;
import com.project.game.match.dto.MatchRoomEnterResponse;
import com.project.game.match.dto.MatchRoomGetResponse;
import com.project.game.match.dto.MatchRoomUpsertResponse;
import com.project.game.play.dto.PlayEndResponse;
import com.project.game.play.dto.PlayGreetingResponse;
import com.project.game.play.dto.PlayReadyRequest;
import com.project.game.play.dto.PlayReadyResponse;
import com.project.game.play.dto.PlayStartResponse;
import com.project.game.play.dto.PlayTurnRequest;
import com.project.game.play.dto.PlayTurnResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface MatchService {

    Slice<MatchRoomGetResponse> findMatchRoomList(Pageable pageable);

    MatchRoomUpsertResponse saveMatchRoom(Long characterId);

    MatchRoomEnterResponse matchRoomEnter(Long characterId, MatchRoomEnterRequest matchRoomEnterRequest);

    PlayReadyResponse ready(Long characterId, Long matchId, PlayReadyRequest playReadyRequest);

    PlayGreetingResponse greeting(Long characterId);

    PlayStartResponse start(Long characterId, Long matchId);

    PlayTurnResponse gameTurn(Long characterId, Long matchId, PlayTurnRequest playTurnRequest);

    PlayEndResponse gameEnd(Long characterId, Long matchId);
}
