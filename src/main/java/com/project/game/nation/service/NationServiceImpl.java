package com.project.game.nation.service;

import static com.project.game.nation.dto.NationUpsertRequest.toEntity;

import com.project.game.nation.dto.NationGetListResponse;
import com.project.game.nation.domain.Nation;
import com.project.game.nation.dto.NationUpsertRequest;
import com.project.game.nation.dto.NationUpsertResponse;
import com.project.game.nation.repository.NationRepository;
import com.project.game.nation.service.usecase.NationService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class NationServiceImpl implements NationService {

    private final NationRepository nationRepository;

    @Override
    public List<NationGetListResponse> getNationList() {
        List<Nation> nations = nationRepository.findAll();
        List<NationGetListResponse> nationResponseList = nations.stream().map(
            nation -> new NationGetListResponse(nation)
        ).collect(Collectors.toList());
        return nationResponseList;
    }

    @Override
    public NationUpsertResponse saveNation(NationUpsertRequest dto) {
        Optional<Nation> findNation = nationRepository.findByNationNm(dto.getNationNm());

        if(findNation.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "중복된 나라 입력입니다.");
        }
        Nation nation = nationRepository.save(toEntity(dto));
        return new NationUpsertResponse(nation);
    }
}
