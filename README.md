# 🕹️실시간 양방향 턴제 RPG
<p align="center">
  <img src="https://github.com/Charge-Evolution-Battle-on-Earth/Server/assets/31121731/b7cd24b8-c900-4126-afb7-a5ee291ba881" alt="진화전투_로고">    
</p>


[![GitHub Release](https://img.shields.io/github/v/release/Charge-Evolution-Battle-on-Earth/Client)](https://github.com/Charge-Evolution-Battle-on-Earth/Client/releases)
- 디자인: [Figma](https://www.figma.com/file/VOS1PohXqLBLN3Ouehbsmm/%EA%B2%8C%EC%9E%84?type=design&node-id=60%3A2&mode=design&t=2WHgNObeB5Et7cu7-1)
- ERD: [ERD](https://dbdiagram.io/d/6501735a02bd1c4a5e7a76d2)
  
<br>

## 프로젝트 소개
안정적인 실시간 양방향 통신 서비스를 제공하기 위해 순수 WebSocket을 활용하여 메세지 파싱과 라우팅, 예외 처리 시스템을 구현하였습니다.
이 과정에서 메세지 프로토콜 정의, 동시성 문제 해결, AOP을 통한 예외처리 등 다양한 어려움을 해결하였습니다.

- CEBONE은 실시간 양방향 통신을 통해 상대 플레이어와 턴을 번갈아 가며 전투를 하는 게임입니다.
- 플레이어는 상점에서 원하는 무기와 장비 등 아이템을 통해 자신의 캐릭터를 강화할 수 있습니다.
- 로비에서 매칭방을 찾아 전투할 플레이어를 선택할 수 있습니다.
- 전투를 완료하면 획득하는 경험치를 통해 레벨 업을 할 수 있으며 이때 전략적으로 새로운 스킬을 습득하여 성장 방향을 결정할 수 있습니다.

<br>

### 💻 개발 환경
- Version : Java 17
- IDE : IntelliJ
- Framework : SpringBoot 3.1.5
- ORM : JPA
- Real Time Networking : WebSocket

### 🧩 ERD
![CEBONE_ERD](https://github.com/Charge-Evolution-Battle-on-Earth/Server/assets/31121731/ed8d9154-db4b-4327-a609-4d2f8d0bda0f)

### 인게임 시연
![CEBONE](https://github.com/Charge-Evolution-Battle-on-Earth/Server/assets/31121731/d6f979fd-991d-4fa7-bf4b-fe8b2e755ae1)



## 실시간 양방향 통신 시스템
### 📝 WebSocket 메세지 프로토콜

``` JSON
{
    "command": "COMMAND",
    "matchId": "long",
    "request":{ }
}
```

### 📝 WebSocket 세션 관리 - 동시성 문제 해결
``` Java
@Component
public class WebSocketSessionManager {

    private Map<Long, WebSocketSession> webSocketSessionMap = new ConcurrentHashMap<>();

    public synchronized void addWebSocketSessionMap(Long key, WebSocketSession socketSession) {
        if (!validateSession(socketSession)) {
            throw new WebSocketSessionInvalidException();
        }
        this.webSocketSessionMap.put(key, socketSession);
    }

    public synchronized void removeWebSocketSessionMap(Long key) {
        this.webSocketSessionMap.remove(key);
    }

    public synchronized void sendMessage(String message, List<Long> keys) throws IOException {
        for (Long key : keys) {
            WebSocketSession socketSession = this.webSocketSessionMap.get(key);
            if (validateSession(socketSession)) {
                socketSession.sendMessage(new TextMessage(message));
            }
        }
    }

    public synchronized boolean validateSession(WebSocketSession socketSession) {
        return socketSession != null && socketSession.isOpen();
    }
}
```

### 📝 WebSocket 메세지 파싱 및 라우팅
<b> 메시지 파싱 </b>
``` Java
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
```

<b> 메세지 라우팅</b>
``` Java
private void dispatcher(Long characterId, Long matchId, JsonObject request,
        List<Long> matchPlayers, WebSocketCommand webSocketCommand)
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
```
### AOP 기반 WebSocket 예외 처리
``` Java
@Aspect
@Component
@RequiredArgsConstructor
public class WebSocketExceptionAspect {

    private final JsonUtil jsonUtil;

    @Around("execution(* com.project.game.play.controller.PlayController.*(..))")
    public Object handleExceptionAndLog(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (WebSocketException we) {
            ErrorResponse response = new ErrorResponse(we);
            return jsonUtil.toJson(response);
        } catch (Throwable e) {
            ErrorResponse response = new ErrorResponse(e);
            return jsonUtil.toJson(response);
        }
    }
}
```
