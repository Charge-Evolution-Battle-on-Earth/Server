package com.project.game.common.handler;

import static com.project.game.common.util.JsonUtil.extractProperty;
import static com.project.game.common.util.JsonUtil.jsonParseToDto;
import static com.project.game.common.util.WebSocketSessionUtil.extractCharacterId;
import static com.project.game.common.websocket.JsonProperty.COMMAND;
import static com.project.game.common.websocket.JsonProperty.MATCH_ID;
import static com.project.game.common.websocket.JsonProperty.REQUEST;
import static com.project.game.common.websocket.WebSocketCommand.findByCommand;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.project.game.common.exception.ValueInvalidException;
import com.project.game.common.websocket.WebSocketCommand;
import com.project.game.common.websocket.WebSocketManager;
import com.project.game.match.dto.MatchRoomPlayerGetResponse;
import com.project.game.match.service.MatchService;
import com.project.game.play.controller.PlayController2;
import com.project.game.play.dto.PlayGreetingResponse;
import com.project.game.play.dto.PlayReadyRequest;
import com.project.game.play.dto.PlayReadyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
public class WebSocketMessageHandler extends TextWebSocketHandler {

    private final PlayController2 playController2;
    private final MatchService matchService;
    private final WebSocketManager webSocketManager;


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
        throws Exception {
        Gson gson = new Gson();
        Long characterId = null;
        try {
            //message parsing
            characterId = extractCharacterId(session, Long.class);
            JsonObject jsonObject = gson.fromJson(message.getPayload(), JsonObject.class);
            Long matchId = extractProperty(jsonObject, MATCH_ID, Long.class);
            JsonObject request = extractProperty(jsonObject, REQUEST, JsonObject.class);
            String command = extractProperty(jsonObject, COMMAND, String.class);

            MatchRoomPlayerGetResponse matchPlayers = matchService.findPlayerByMatchId(matchId);
            WebSocketCommand webSocketCommand = findByCommand(command);

            dispatcher(characterId, matchId, request, matchPlayers, webSocketCommand);
        } catch (Exception e) {
            webSocketManager.sendMessage(e.getMessage(), characterId);
        }
    }

    //TODO 디자인 패턴 중 일반화 시킬 수 있는 방법을 찾아서 리팩토링하자
    private void dispatcher(Long characterId, Long matchId, JsonObject request,
        MatchRoomPlayerGetResponse matchPlayers, WebSocketCommand webSocketCommand)
        throws Exception {
        Gson gson = new Gson();

        //message routing
        switch (webSocketCommand) {
            case GREETING -> {
                //execute
                PlayGreetingResponse response = playController2.greeting(characterId);
                //message send
                webSocketManager.sendMessage(response.getGreetingMessage(),
                    matchPlayers.getHostId(), matchPlayers.getEntrantId());
            }
            case READY -> {
                //execute
                PlayReadyResponse playReadyResponse = playController2.ready(
                    String.valueOf(matchId),
                    characterId,
                    jsonParseToDto(request,
                        PlayReadyRequest.class));
                //message send

                webSocketManager.sendMessage(gson.toJson(playReadyResponse), characterId);
            }
            case EMPTY -> throw new ValueInvalidException();
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        webSocketManager.addWebSocketSessionMap(
            (Long) session.getAttributes().get("characterId"), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        webSocketManager.removeWebSocketSessionMap(
            (Long) session.getAttributes().get("characterId"));
    }
}