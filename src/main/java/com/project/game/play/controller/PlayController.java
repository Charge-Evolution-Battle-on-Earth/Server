package com.project.game.play.controller;

import static com.project.game.common.util.SimpMessageHeaderAccessorUtil.extractCharacterIdFromAccessor;

import com.project.game.match.service.MatchService;
import com.project.game.play.dto.PlayReadyRequest;
import com.project.game.play.dto.PlayReadyResponse;
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

    @MessageMapping("/enter/{matchId}")
    @SendTo("/topic/{matchId}/message")
    private PlayReadyResponse enter(@DestinationVariable String matchId, PlayReadyRequest playReadyRequest){
        return null;
    }

    @MessageMapping("ready/{matchId}")
    @SendTo("/topic/{matchId}/message")
    private PlayReadyResponse ready(@DestinationVariable String matchId, PlayReadyRequest playReadyRequest,  SimpMessageHeaderAccessor accessor){
        Long characterId = extractCharacterIdFromAccessor(accessor);
        PlayReadyResponse response = matchService.ready(characterId, Long.parseLong(matchId), playReadyRequest);
        return response;
    }
}
