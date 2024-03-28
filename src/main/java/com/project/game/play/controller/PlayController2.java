package com.project.game.play.controller;

import com.project.game.match.service.MatchService;
import com.project.game.play.dto.PlayGreetingResponse;
import com.project.game.play.dto.PlayReadyRequest;
import com.project.game.play.dto.PlayReadyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayController2 {

    private final MatchService matchService;

    public PlayGreetingResponse greeting(Long characterId) {
        return matchService.greeting(characterId);
    }

    public PlayReadyResponse ready(String matchId, Long characterId,
        PlayReadyRequest playReadyRequest) {
        return matchService.ready(characterId, Long.parseLong(matchId),
            playReadyRequest);
    }
}
