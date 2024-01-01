package com.project.game.play.controller;

import com.project.game.play.dto.PlayReadyRequest;
import com.project.game.play.dto.PlayReadyResponse;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class PlayController {

    @MessageMapping("TTT")
    @SendTo("/topic/message")
    // TODO: remove
    public String greeting(String message){
        return "test";
    }

    @MessageMapping("ready/{matchId}")
    @SendTo("/topic/{matchId}/message")
    public PlayReadyResponse ready(@DestinationVariable String matchId, PlayReadyRequest playReadyRequest){
        System.out.println("matchId = " + matchId + ", playReadyRequest = " + playReadyRequest);
        System.out.println(playReadyRequest.getReadyStatus() + playReadyRequest.getMatchId());
        return new PlayReadyResponse(playReadyRequest.getMatchId(), !playReadyRequest.getReadyStatus(),true);
    }
}
