package com.project.game.play.controller;

import com.project.game.common.util.JsonUtil;
import com.project.game.match.dto.PlayQuitResponse;
import com.project.game.match.service.MatchService;
import com.project.game.play.dto.PlayEndResponse;
import com.project.game.play.dto.PlayGreetingResponse;
import com.project.game.play.dto.PlayReadyRequest;
import com.project.game.play.dto.PlayReadyResponse;
import com.project.game.play.dto.PlayStartResponse;
import com.project.game.play.dto.PlaySurrenderResponse;
import com.project.game.play.dto.PlayTurnRequest;
import com.project.game.play.dto.PlayTurnResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayController {

    private final MatchService matchService;
    private final JsonUtil jsonUtil;

    public String greeting(Long characterId) throws RuntimeException {
        PlayGreetingResponse response = matchService.greeting(characterId);
        return jsonUtil.toJson(response);
    }

    public String ready(Long characterId, Long matchId, PlayReadyRequest playReadyRequest)
        throws RuntimeException {
        PlayReadyResponse response = matchService.ready(characterId, matchId, playReadyRequest);
        return jsonUtil.toJson(response);
    }

    public String start(Long characterId, Long matchId) throws RuntimeException {
        PlayStartResponse response = matchService.start(characterId, matchId);
        return jsonUtil.toJson(response);
    }

    public String turnGame(Long characterId, Long matchId, PlayTurnRequest playTurnRequest)
        throws RuntimeException {
        PlayTurnResponse response = matchService.turnGame(characterId, matchId, playTurnRequest);
        return jsonUtil.toJson(response);
    }

    public String endGame(Long characterId, Long matchId) throws RuntimeException {
        PlayEndResponse response = matchService.endGame(characterId, matchId);
        return jsonUtil.toJson(response);
    }

    public String surrenderGame(Long characterId, Long matchId) throws RuntimeException {
        PlaySurrenderResponse response = matchService.surrenderGame(characterId, matchId);
        return jsonUtil.toJson(response);
    }

    public String quitGame(Long characterId, Long matchId) throws RuntimeException {
        PlayQuitResponse response = matchService.quitGame(characterId, matchId);
        return jsonUtil.toJson(response);
    }
}
