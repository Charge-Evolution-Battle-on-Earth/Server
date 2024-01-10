package com.project.game.play.controller;

import static com.project.game.common.util.SimpMessageHeaderAccessorUtil.extractCharacterIdFromAccessor;

import com.project.game.match.service.MatchService;
import com.project.game.play.dto.PlayEndResponse;
import com.project.game.play.dto.PlayGreetingResponse;
import com.project.game.play.dto.PlayReadyRequest;
import com.project.game.play.dto.PlayReadyResponse;
import com.project.game.play.dto.PlayStartResponse;
import com.project.game.play.dto.PlayTurnRequest;
import com.project.game.play.dto.PlayTurnResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PlayController {

    private final MatchService matchService;

    @MessageMapping("/greeting/{matchId}")
    @SendTo("/topic/{matchId}/message")
    private PlayGreetingResponse greeting(SimpMessageHeaderAccessor accessor){
        Long characterId = extractCharacterIdFromAccessor(accessor);
        //TODO 메세지 관리 방법 고민
        PlayGreetingResponse response = matchService.greeting(characterId);
        return response;
    }

    @MessageMapping("ready/{matchId}")
    @SendTo("/topic/{matchId}/message")
    private PlayReadyResponse ready(@DestinationVariable String matchId, SimpMessageHeaderAccessor accessor, PlayReadyRequest playReadyRequest){
        Long characterId = extractCharacterIdFromAccessor(accessor);
        PlayReadyResponse response = matchService.ready(characterId, Long.parseLong(matchId), playReadyRequest);
        return response;
    }

    @MessageMapping("start/{matchId}")
    @SendTo("/topic/{matchId}/message")
    private PlayStartResponse start(@DestinationVariable String matchId, SimpMessageHeaderAccessor accessor){
        Long characterId = extractCharacterIdFromAccessor(accessor);
        PlayStartResponse response = matchService.start(characterId, Long.parseLong(matchId));
        return response;
    }

    @MessageMapping("game/turn/{matchId}")
    @SendTo("/topic/{matchId}/message")
    private PlayTurnResponse gameTurn(@DestinationVariable String matchId, SimpMessageHeaderAccessor accessor, PlayTurnRequest playTurnRequest){
        Long characterId = extractCharacterIdFromAccessor(accessor);
        PlayTurnResponse response = matchService.gameTurn(characterId, Long.parseLong(matchId), playTurnRequest);
        return response;
    }

    @MessageMapping("game/end/{matchId}")
    @SendTo("/topic/{matchId}/message")
    private PlayEndResponse gameEnd(@DestinationVariable String matchId, SimpMessageHeaderAccessor accessor){
        Long characterId = extractCharacterIdFromAccessor(accessor);
        PlayEndResponse response = matchService.gameEnd(characterId, Long.parseLong(matchId));
        return response;
    }
}
