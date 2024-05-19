package com.project.game.websocket.handler;

import static com.project.game.common.util.WebSocketSessionUtil.extractCharacterId;
import static com.project.game.websocket.constant.WebSocketCommand.findWebSocketCommand;

import com.google.gson.JsonObject;
import com.project.game.common.util.JsonUtil;
import com.project.game.match.service.MatchService;
import com.project.game.match.vo.MatchPlayer;
import com.project.game.play.controller.PlayController;
import com.project.game.play.dto.PlayReadyRequest;
import com.project.game.play.dto.PlayTurnRequest;
import com.project.game.websocket.WebSocketSessionManager;
import com.project.game.websocket.constant.WebSocketCommand;
import com.project.game.websocket.dto.WebSocketMessageRequest;
import com.project.game.websocket.exception.CharacterNotInMatchException;
import com.project.game.websocket.exception.InvalidWebSocketMessageException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
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
    private final WebSocketSessionManager webSocketSessionManager;
    private final JsonUtil jsonUtil;

    /**
     * 인게임 플레이 중 발생하는 WebSocket 기반 요청들을 처리하는 핵심 메소드. 메세지 파싱과 라우팅을 수행함.
     *
     * @param session WebSocketSession
     * @param message WebSocketMessageProtocol 형식을 준수한 메세지
     * @throws IOException
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
        throws IOException {
        Long characterId = null;
        try {
            characterId = extractCharacterId(session, Long.class);
            WebSocketMessageRequest messageRequest = jsonUtil.parseWebSocketMessage(
                message.getPayload());
            Long matchId = messageRequest.matchId();
            JsonObject request = messageRequest.request();
            String command = messageRequest.command();

            MatchPlayer matchPlayers = matchService.findPlayerByMatchId(matchId);
            List<Long> matchPlayersList = matchPlayers.toList();

            if (!matchPlayers.isContainsPlayer(characterId)) {
                throw new CharacterNotInMatchException(matchId);
            }

            WebSocketCommand webSocketCommand = findWebSocketCommand(command);

            dispatcher(characterId, matchId, request, matchPlayersList, webSocketCommand);
        } catch (Exception e) {
            webSocketSessionManager.sendMessage(e.getMessage(),
                Collections.singletonList(characterId));
        }
    }

    // TODO 규모가 커질 경우 디자인 패턴 중 일반화 시킬 수 있는 방법을 찾아서 리팩토링하자
    private void dispatcher(Long characterId, Long matchId, JsonObject request,
        List<Long> matchPlayers, WebSocketCommand webSocketCommand)
        throws Exception {
        String responseMessage = new String();

        //message routing
        switch (webSocketCommand) {
            case GREETING -> {
                responseMessage = playController.greeting(characterId, matchId);
            }
            case READY -> {
                PlayReadyRequest playReadyRequest = jsonUtil.fromJson(request,
                    PlayReadyRequest.class);
                responseMessage = playController.ready(characterId, matchId
                    , playReadyRequest);
            }
            case START -> {
                responseMessage = playController.start(characterId, matchId);
            }
            case TURN_GAME -> {
                responseMessage = playController.turnGame(characterId, matchId,
                    jsonUtil.fromJson(request, PlayTurnRequest.class));
            }
            case END_GAME -> {
                responseMessage = playController.endGame(characterId, matchId);
            }
            case SURRENDER_GAME -> {
                responseMessage = playController.surrenderGame(characterId, matchId);
            }
            case QUIT_GAME -> {
                responseMessage = playController.quitGame(characterId, matchId);
            }
            case EMPTY -> throw new InvalidWebSocketMessageException(matchId);
        }
        webSocketSessionManager.sendMessage(responseMessage, matchPlayers);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        webSocketSessionManager.addWebSocketSessionMap(extractCharacterId(session, Long.class),
            session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // TODO 강제 종료에 대한 처리 필요 (ex match room 관리, match 결과에 대한 처리, reconnection 시 어떻게 처리할 것인지 등)
        try (session) {
            webSocketSessionManager.removeWebSocketSessionMap(
                extractCharacterId(session, Long.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}