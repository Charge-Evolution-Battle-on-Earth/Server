package com.project.game.match.service;

import static com.project.game.match.domain.MatchRoom.makeStakedGold;
import static com.project.game.match.vo.MatchStatus.FINISHED;
import static com.project.game.match.vo.MatchStatus.IN_PROGRESS;
import static com.project.game.match.vo.MatchStatus.READY;
import static com.project.game.match.vo.MatchStatus.WAITING;
import static com.project.game.match.vo.PlayerType.ENTRANT;
import static com.project.game.match.vo.PlayerType.HOST;
import static com.project.game.match.vo.PlayerType.togglePlayerType;

import com.project.game.character.domain.Character;
import com.project.game.character.dto.CharacterSkillGetResponse;
import com.project.game.character.exception.CharacterNotFoundException;
import com.project.game.character.exception.CharacterSkillNotFoundException;
import com.project.game.character.repository.CharacterRepository;
import com.project.game.character.repository.CharacterSkillRepository;
import com.project.game.character.service.CharacterService;
import com.project.game.common.domain.Stat;
import com.project.game.level.service.LevelService;
import com.project.game.match.domain.MatchHistory;
import com.project.game.match.domain.MatchRoom;
import com.project.game.match.dto.MatchRoomEnterRequest;
import com.project.game.match.dto.MatchRoomEnterResponse;
import com.project.game.match.dto.MatchRoomGetResponse;
import com.project.game.match.dto.MatchRoomUpsertResponse;
import com.project.game.match.dto.PlayQuitResponse;
import com.project.game.match.exception.LevelDifferenceInvalidException;
import com.project.game.match.exception.MatchRoomDuplicateParticipantException;
import com.project.game.match.exception.MatchRoomFullException;
import com.project.game.match.exception.MatchRoomNotFoundException;
import com.project.game.match.exception.MatchRoomTurnInvalidException;
import com.project.game.match.exception.PlayerTypeNotHostException;
import com.project.game.match.repository.MatchHistoryRepository;
import com.project.game.match.repository.MatchRoomRepository;
import com.project.game.match.vo.MatchPlayer;
import com.project.game.match.vo.PlayerType;
import com.project.game.play.dto.PlayEndResponse;
import com.project.game.play.dto.PlayGreetingResponse;
import com.project.game.play.dto.PlayReadyRequest;
import com.project.game.play.dto.PlayReadyResponse;
import com.project.game.play.dto.PlayStartResponse;
import com.project.game.play.dto.PlaySurrenderResponse;
import com.project.game.play.dto.PlayTurnRequest;
import com.project.game.play.dto.PlayTurnResponse;
import com.project.game.skill.domain.Skill;
import com.project.game.skill.domain.SkillEffect;
import com.project.game.skill.dto.SkillNotFoundException;
import com.project.game.skill.repository.SkillEffectRepository;
import com.project.game.skill.repository.SkillRepository;
import com.project.game.websocket.WebSocketSessionManager;
import com.project.game.websocket.exception.MatchStatusInvalidException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRoomRepository matchRoomRepository;
    private final CharacterRepository characterRepository;
    private final CharacterSkillRepository characterSkillRepository;
    private final CharacterService characterService;
    private final SkillRepository skillRepository;
    private final SkillEffectRepository skillEffectRepository;
    private final MatchHistoryRepository matchHistoryRepository;
    private final LevelService levelService;
    private final WebSocketSessionManager webSocketSessionManager;

    @Value("${game.win-exp}")
    private Integer winExp;
    @Value("${game.lose-exp}")
    private Integer loseExp;

    @Override
    public MatchPlayer findPlayerByMatchId(Long matchId) {
        MatchRoom matchRoom = matchRoomRepository.findById(matchId)
            .orElseThrow(() -> new MatchRoomNotFoundException(matchId));

        return new MatchPlayer(
            matchRoom.getHost() != null ? matchRoom.getHost().getCharacterId() : null,
            matchRoom.getEntrant() != null ? matchRoom.getEntrant().getCharacterId() : null);
    }

    @Override
    public Slice<MatchRoomGetResponse> findMatchRoomList(Pageable pageable) {
        Slice<MatchRoom> matchRooms = matchRoomRepository.findAllByPaging(pageable);
        List<MatchRoomGetResponse> responseList = matchRooms.stream()
            .map(matchRoom -> new MatchRoomGetResponse(matchRoom)).collect(
                Collectors.toList());

        return new SliceImpl<>(responseList, pageable, matchRooms.hasNext());
    }

    @Override
    @Transactional
    public MatchRoomUpsertResponse saveMatchRoom(Long characterId) {
        Character host = characterRepository.findById(characterId)
            .orElseThrow(() -> new CharacterNotFoundException(characterId));

        // 자신이 이미 방에 참여 중인 경우 방을 생성할 수없다.
        if (webSocketSessionManager.isContainsKey(characterId)) {
            throw new MatchRoomDuplicateParticipantException(characterId);
        }

        MatchRoom matchRoom = matchRoomRepository.save(
            MatchRoom.builder().host(host).matchStatus(WAITING).stakedGold(makeStakedGold(
                host.getLevelId())).build());

        return new MatchRoomUpsertResponse(matchRoom);
    }

    @Override
    @Transactional
    public MatchRoomEnterResponse enterMatchRoom(Long characterId,
        MatchRoomEnterRequest matchRoomEnterRequest) {
        Character entrant = characterRepository.findById(characterId)
            .orElseThrow(() -> new CharacterNotFoundException(characterId));
        MatchRoom matchRoom = matchRoomRepository.findById(matchRoomEnterRequest.getMatchRoomId())
            .orElseThrow(() -> new MatchRoomNotFoundException(
                matchRoomEnterRequest.getMatchRoomId()));

        //입장 가능 여부 확인
        //조건 1. 방에 entrant 자리는 비어 있어야함.
        if (matchRoom.getEntrant() != null) {
            throw new MatchRoomFullException(matchRoom.getMatchRoomId());
        }

        //조건 2. 자신이 이미 방에 참여 중인 경우 참여자로 입장할 수없다.
        if (webSocketSessionManager.isContainsKey(characterId)) {
            throw new MatchRoomDuplicateParticipantException(characterId);
        }

        //조건 3. 레벨 차이가 2 이하이여야 함.
        Integer levelDifference = matchRoom.getHost().getLevelId() - entrant.getLevelId();
        if (Math.abs(levelDifference) > 2) {
            throw new LevelDifferenceInvalidException(levelDifference);
        }

        //입장 처리
        matchRoom.setEntrant(entrant);

        return new MatchRoomEnterResponse(matchRoom);
    }

    /**
     * ready를 시도한 사용자의 준비 상태를 토글하여 matchRoom의 host 혹은 entrant의 준비 상태 변경
     *
     * @param characterId
     * @param matchId
     * @param playReadyRequest
     * @return PlayReadyResponse
     */
    @Override
    @Transactional
    public PlayReadyResponse ready(Long characterId, Long matchId,
        PlayReadyRequest playReadyRequest) {
        MatchRoom matchRoom = matchRoomRepository.findById(matchId)
            .orElseThrow(() -> new MatchRoomNotFoundException(matchId));
        PlayerType playerType = matchRoom.getPlayerType(characterId);
        boolean hostReadyStatus;
        boolean entrantReadyStatus;

        if (playerType.equals(HOST)) {
            hostReadyStatus = !playReadyRequest.getHostReadyStatus();
            entrantReadyStatus = playReadyRequest.getEntrantReadyStatus();
        } else {
            hostReadyStatus = playReadyRequest.getHostReadyStatus();
            entrantReadyStatus = !playReadyRequest.getEntrantReadyStatus();
        }

        //매치 방 상태 변경
        if (hostReadyStatus && entrantReadyStatus) {
            matchRoom.setMatchStatus(READY);
        } else {
            matchRoom.setMatchStatus(WAITING);
        }

        return new PlayReadyResponse(hostReadyStatus, entrantReadyStatus,
            matchRoom.getMatchStatus());
    }

    @Override
    public PlayGreetingResponse greeting(Long characterId) {
        return new PlayGreetingResponse(characterId + "님이 입장하였습니다.");
    }

    @Override
    @Transactional
    public PlayStartResponse start(Long characterId, Long matchId) {
        MatchRoom matchRoom = matchRoomRepository.findById(matchId)
            .orElseThrow(() -> new MatchRoomNotFoundException(matchId));

        //READY 이외의 상태일 경우 예외 처리
        if (!matchRoom.getMatchStatus().equals(READY)) {
            throw new MatchStatusInvalidException(matchId);
        }

        Character host = matchRoom.getHost();
        Character entrant = matchRoom.getEntrant();

        //Player1, Player2 스탯과 스킬 리스트
        Stat hostTotalStat = characterService.getCharacterTotalStat(host);
        Stat entrantTotalStat = characterService.getCharacterTotalStat(entrant);

        List<CharacterSkillGetResponse> hostSkillList = characterService.getCharacterSkills(
            host.getCharacterId());
        List<CharacterSkillGetResponse> entrantSkillList = characterService.getCharacterSkills(
            entrant.getCharacterId());

        //선제 공격 플레이어 선정(spd 높은 플레이어)
        if (hostTotalStat.getSpd() > entrantTotalStat.getSpd()) {
            matchRoom.setTurnOwner(HOST);
        } else {
            matchRoom.setTurnOwner(ENTRANT);
        }

        //Match 상태 업데이트
        matchRoom.setHostStatAndStartHp(hostTotalStat);
        matchRoom.setEntrantStatAndStartHp(entrantTotalStat);
        matchRoom.setMatchStatus(IN_PROGRESS);

        return new PlayStartResponse(matchRoom.getMatchStatus(), hostTotalStat, entrantTotalStat,
            hostSkillList,
            entrantSkillList, matchRoom.getTurnOwner());
    }

    @Override
    @Transactional
    public PlayTurnResponse turnGame(Long characterId, Long matchId,
        PlayTurnRequest playTurnRequest) {
        MatchRoom matchRoom = matchRoomRepository.findById(matchId)
            .orElseThrow(() -> new MatchRoomNotFoundException(matchId));
        Skill skill = skillRepository.findById(playTurnRequest.getSkillId())
            .orElseThrow(() -> new SkillNotFoundException());

        PlayerType playerType = matchRoom.getPlayerType(characterId);

        //turn owner 요청자 비교 검증
        if (matchRoom.getTurnOwner() != playerType) {
            throw new MatchRoomTurnInvalidException(characterId);
        }

        //IN_PROGRESS 이외의 상태일 경우 예외 처리
        if (!matchRoom.getMatchStatus().equals(IN_PROGRESS)) {
            throw new MatchStatusInvalidException(matchId);
        }

        //skill 보유 여부 검증
        if (!characterSkillRepository.existsByCharacterCharacterIdAndSkillSkillId(characterId,
            skill.getSkillId())) {
            throw new CharacterSkillNotFoundException();
        }

        //skill 효과 처리
        List<SkillEffect> skillEffects = skillEffectRepository.findBySkillSkillId(
            skill.getSkillId());

        matchRoom.payManaCost(playerType, skill.getManaCost());

        for (SkillEffect effect : skillEffects) {
            if (playerType == ENTRANT) {
                matchRoom.effectSkillCastByEntrant(effect);
            } else if (playerType == HOST) {
                matchRoom.effectSkillCastByHost(effect);
            }
        }

        //만약 둘 중 한 플레이어의 체력이 0또는 0 이하가 될 경우 게임 종료 상태 반환
        if (matchRoom.isGameOverWithStat()) {
            matchRoom.setMatchStatus(FINISHED);
            return new PlayTurnResponse(true, matchRoom.getHostStat(), matchRoom.getEntrantStat(),
                skill.getSkillNm());
        } else {
            PlayerType toggleTurnOwner = matchRoom.getToggleTurnOwner();
            matchRoom.setTurnOwner(toggleTurnOwner);
            return new PlayTurnResponse(false, matchRoom.getHostStat(), matchRoom.getEntrantStat(),
                toggleTurnOwner, skill.getSkillNm());
        }
    }

    @Override
    @Transactional
    public PlayEndResponse endGame(Long characterId, Long matchId) {
        MatchRoom matchRoom = matchRoomRepository.findById(matchId)
            .orElseThrow(() -> new MatchRoomNotFoundException(matchId));

        PlayerType playerType = matchRoom.getPlayerType(characterId);

        //종료된 매치인지 확인
        if (matchRoom.getMatchStatus() != FINISHED) {
            throw new MatchStatusInvalidException(matchId);
        }

        //host 사용자의 요청만 허용
        if (playerType != HOST) {
            throw new PlayerTypeNotHostException();
        }

        //winner & loser 확인
        Character winner = matchRoom.getWinner();
        Character loser = matchRoom.getLoser();

        PlayerType winnerType = matchRoom.getWinnerType();
        PlayerType loserType = togglePlayerType(winnerType);

        processGameResult(matchRoom, winner, loser);

        return new PlayEndResponse(winnerType, loserType, winner, loser, matchRoom);
    }

    @Override
    @Transactional
    public PlaySurrenderResponse surrenderGame(Long characterId, Long matchId) {
        MatchRoom matchRoom = matchRoomRepository.findById(matchId)
            .orElseThrow(() -> new MatchRoomNotFoundException(matchId));

        PlayerType playerType = matchRoom.getPlayerType(characterId);

        //진행 중인 매치인지 확인
        if (matchRoom.getMatchStatus() != IN_PROGRESS) {
            throw new MatchStatusInvalidException(matchId);
        }

        //winner & loser 확인
        Character winner;
        Character loser;

        PlayerType winnerType = playerType;
        PlayerType loserType = togglePlayerType(winnerType);

        if (winnerType == HOST) {
            winner = matchRoom.getHost();
            loser = matchRoom.getEntrant();
        } else {
            winner = matchRoom.getEntrant();
            loser = matchRoom.getHost();
        }

        processGameResult(matchRoom, winner, loser);

        return new PlaySurrenderResponse(winnerType, loserType, winner, loser, matchRoom);
    }

    @Override
    @Transactional
    public PlayQuitResponse quitGame(Long characterId, Long matchId) {
        MatchRoom matchRoom = matchRoomRepository.findById(matchId)
            .orElseThrow(() -> new MatchRoomNotFoundException(matchId));

        PlayerType playerType = matchRoom.getPlayerType(characterId);
        Character player = characterRepository.findById(characterId)
            .orElseThrow(() -> new CharacterNotFoundException(characterId));

        //대기 중인 매치인지 확인
        if (matchRoom.getMatchStatus() != WAITING) {
            throw new MatchStatusInvalidException(matchId);
        }

        if (playerType == ENTRANT) {
            //입장자일 경우 요청자 데이터 삭제
            matchRoom.setEntrant(null);
        } else if (playerType == HOST) {
            if (matchRoom.getEntrant() == null) {
                //남은 사용자가 없는 경우 매칭방 삭제
                matchRoomRepository.delete(matchRoom);
            } else {
                matchRoom.setHost(matchRoom.getEntrant());
                matchRoom.setEntrant(null);
            }
        }

        //웹소켓 세션 제거
        webSocketSessionManager.removeWebSocketSessionMap(characterId);

        return new PlayQuitResponse(player, playerType);
    }

    /**
     * 매칭 결과 정산 money & exp
     */
    @Transactional
    private void processGameResult(MatchRoom matchRoom, Character winner, Character loser) {
        Integer winnerGold = matchRoom.getWinnerGold(winner.getLevelId());
        Integer loserGold = matchRoom.getLoserGold(loser.getLevelId());
        Integer winnerExp = winExp;
        Integer loserExp = loseExp;

        //게임 결과 보상 부여
        winner.plusMoney(winnerGold);
        winner.plusExp(winnerExp);
        loser.plusMoney(loserGold);
        loser.plusExp(loserExp);

        //levelUp 여부 확인 및 처리
        levelService.checkAndProcessLevelUp(winner.getCharacterId());
        levelService.checkAndProcessLevelUp(loser.getCharacterId());

        //match history 저장
        MatchHistory matchHistory = MatchHistory.builder().matchRoom(matchRoom).winner(winner)
            .loser(loser).stakedGold(matchRoom.getStakedGold()).build();
        matchHistoryRepository.save(matchHistory);

        //매칭 상태 초기화
        matchRoom.setInitMatchRoom();
    }
}
