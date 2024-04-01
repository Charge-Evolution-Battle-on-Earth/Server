package com.project.game.common.handler;

import static com.project.game.common.util.WebSocketSessionUtil.extractCharacterId;
import static com.project.game.common.websocket.JsonProperty.COMMAND;
import static com.project.game.common.websocket.JsonProperty.MATCH_ID;
import static com.project.game.common.websocket.JsonProperty.REQUEST;
import static com.project.game.common.websocket.WebSocketCommand.findByCommand;

import com.google.gson.JsonObject;
import com.project.game.common.exception.ValueInvalidException;
import com.project.game.common.util.JsonUtil;
import com.project.game.common.websocket.WebSocketCommand;
import com.project.game.common.websocket.WebSocketManager;
import com.project.game.match.dto.MatchRoomPlayerGetResponse;
import com.project.game.match.service.MatchService;
import com.project.game.play.controller.PlayController;
import com.project.game.play.dto.PlayReadyRequest;
import com.project.game.play.dto.PlayTurnRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
public class WebSocketMessageHandler extends TextWebSocketHandler {

    private final PlayController playController;
    private final MatchService matchService;
    private final WebSocketManager webSocketManager;
    private final JsonUtil jsonUtil;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
        throws Exception {
        Long characterId = null;
        try {
            //message parsing
            characterId = extractCharacterId(session, Long.class);
            JsonObject jsonObject = jsonUtil.fromJson(message.getPayload(), JsonObject.class);
            Long matchId = jsonUtil.extractProperty(jsonObject, MATCH_ID, Long.class);
            JsonObject request = jsonUtil.extractProperty(jsonObject, REQUEST, JsonObject.class);
            String command = jsonUtil.extractProperty(jsonObject, COMMAND, String.class);

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
        String responseMessage = new String();

        //message routing
        switch (webSocketCommand) {
            case GREETING -> {
                responseMessage = playController.greeting(characterId);
            }
            case READY -> {
                PlayReadyRequest playReadyRequest = jsonUtil.fromJson(request,
                    PlayReadyRequest.class);
                responseMessage = playController.ready(characterId, matchId
                    , playReadyRequest);
            }
            case START -> {
                playController.start(characterId, matchId);
            }
            case TURN_GAME -> {
                playController.turnGame(characterId, matchId,
                    jsonUtil.fromJson(request, PlayTurnRequest.class));
            }
            case END_GAME -> {
                playController.endGame(characterId, matchId);
            }
            case SURRENDER_GAME -> {
                playController.surrenderGame(characterId, matchId);
            }
            case QUIT_GAME -> {
                playController.quitGame(characterId, matchId);
            }
            case EMPTY -> throw new ValueInvalidException();
        }
        webSocketManager.sendMessage(responseMessage, matchPlayers.getHostId(),
            matchPlayers.getEntrantId());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        webSocketManager.addWebSocketSessionMap(extractCharacterId(session, Long.class), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        webSocketManager.removeWebSocketSessionMap(extractCharacterId(session, Long.class));
    }
}