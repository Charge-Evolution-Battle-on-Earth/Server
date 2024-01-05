package com.project.game.match.service;

import static com.project.game.match.domain.MatchRoom.makeStakedGold;
import static com.project.game.match.domain.MatchStatus.WAITING;

import com.project.game.character.domain.Character;
import com.project.game.character.exception.CharacterNotFoundException;
import com.project.game.character.repository.CharacterRepository;
import com.project.game.match.domain.MatchRoom;
import com.project.game.match.dto.MatchRoomEnterRequest;
import com.project.game.match.dto.MatchRoomEnterResponse;
import com.project.game.match.dto.MatchRoomGetResponse;
import com.project.game.match.dto.MatchRoomUpsertResponse;
import com.project.game.match.exception.InvalidLevelDifferenceException;
import com.project.game.match.exception.MatchRoomFullException;
import com.project.game.match.exception.MatchRoomNotFoundException;
import com.project.game.match.repository.MatchRoomRepository;
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
            throw new InvalidLevelDifferenceException(levelDifference);
        }

        //입장 처리
        matchRoom.setEntrant(entrant);

        return new MatchRoomEnterResponse(matchRoom);
    }
}
