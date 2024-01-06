package com.project.game.match.service;

import static com.project.game.match.domain.MatchRoom.makeStakedGold;
import static com.project.game.match.vo.MatchStatus.READY;
import static com.project.game.match.vo.MatchStatus.WAITING;

import com.project.game.character.domain.Character;
import com.project.game.character.exception.CharacterNotFoundException;
import com.project.game.character.repository.CharacterRepository;
import com.project.game.match.domain.MatchRoom;
import com.project.game.match.dto.MatchRoomEnterRequest;
import com.project.game.match.dto.MatchRoomEnterResponse;
import com.project.game.match.dto.MatchRoomGetResponse;
import com.project.game.match.dto.MatchRoomUpsertResponse;
import com.project.game.match.exception.LevelDifferenceInvalidException;
import com.project.game.match.exception.MatchRoomFullException;
import com.project.game.match.exception.MatchRoomNotFoundException;
import com.project.game.match.exception.PlayerTypeInvalidException;
import com.project.game.match.repository.MatchRoomRepository;
import com.project.game.match.vo.MatchStatus;
import com.project.game.match.vo.PlayerType;
import com.project.game.play.dto.PlayReadyRequest;
import com.project.game.play.dto.PlayReadyResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService{

    private final MatchRoomRepository matchRoomRepository;
    private final CharacterRepository characterRepository;

    @Override
    public Slice<MatchRoomGetResponse> findMatchRoomList(Pageable pageable) {
        Slice<MatchRoom> matchRooms = matchRoomRepository.findAllByPaging(pageable);
        List<MatchRoomGetResponse> responseList = matchRooms.stream().map(matchRoom -> new MatchRoomGetResponse(matchRoom)).collect(
            Collectors.toList());

        return new SliceImpl<>(responseList, pageable, matchRooms.hasNext());
    }

    @Override
    public MatchRoomUpsertResponse saveMatchRoom(Long characterId) {
        Character creator = characterRepository.findById(characterId)
            .orElseThrow(() -> new CharacterNotFoundException(characterId));
        MatchRoom matchRoom = matchRoomRepository.save(MatchRoom.builder().creator(creator).matchStatus(WAITING).stakedGold(makeStakedGold(
            creator.getLevelId())).build());

        return new MatchRoomUpsertResponse(matchRoom);
    }

    @Override
    public MatchRoomEnterResponse matchRoomEnter(Long characterId,
        MatchRoomEnterRequest matchRoomEnterRequest) {
        Character entrant = characterRepository.findById(characterId)
            .orElseThrow(() -> new CharacterNotFoundException(characterId));
        MatchRoom matchRoom = matchRoomRepository.findById(matchRoomEnterRequest.getMatchRoomId()).orElseThrow(()-> new MatchRoomNotFoundException(
            matchRoomEnterRequest.getMatchRoomId()));

        //입장 가능 여부 확인
        //조건 1. 방에 entrant 자리는 비어 있어야함.
        if(matchRoom.getEntrant() != null){
            throw new MatchRoomFullException(matchRoom.getMatchRoomId());
        }

        //조건 2. 레벨 차이가 2 이하이여야 함.
        Integer levelDifference = matchRoom.getCreator().getLevelId() - entrant.getLevelId();
        if(Math.abs(levelDifference) > 2){
            throw new LevelDifferenceInvalidException(levelDifference);
        }

        //입장 처리
        matchRoom.setEntrant(entrant);

        return new MatchRoomEnterResponse(matchRoom);
    }

    @Override
    public PlayReadyResponse ready(Long characterId, Long matchId, PlayReadyRequest playReadyRequest) {
        MatchRoom matchRoom = matchRoomRepository.findById(matchId)
            .orElseThrow(() -> new MatchRoomNotFoundException(matchId));
        PlayerType playerType = matchRoom.getPlayerType(characterId);

        Boolean creatorReadyStatus = playReadyRequest.getCreatorReadyStatus();
        Boolean entrantReadyStatus = playReadyRequest.getEntrantReadyStatus();

        //플레이어의 준비 상태 변경
        if(playerType.equals(PlayerType.CREATOR)) {
            creatorReadyStatus = !creatorReadyStatus;
        }
        else if(playerType.equals(PlayerType.ENTRANT)){
            entrantReadyStatus = !entrantReadyStatus;
        }
        else{
            throw new PlayerTypeInvalidException(characterId);
        }

        if(creatorReadyStatus && entrantReadyStatus){
            matchRoom.setMatchStatus(READY);
        }else {
            matchRoom.setMatchStatus(WAITING);
        }

        return new PlayReadyResponse(creatorReadyStatus, entrantReadyStatus, matchRoom.getMatchStatus());
    }
}
