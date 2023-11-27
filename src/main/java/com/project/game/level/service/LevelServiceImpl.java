package com.project.game.level.service;

import com.project.game.level.dto.LevelUpResponse;
import com.project.game.level.domain.Level;
import com.project.game.level.repository.LevelRepository;
import com.project.game.level.service.usecase.LevelService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LevelServiceImpl implements LevelService {

    private final LevelRepository levelRepository;

    @Override
    public LevelUpResponse levelUp(Integer levelId) {
        Optional<Level> level = levelRepository.findById(levelId);

        if(level.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 레벨입니다.");
        }
        
        //TODO 캐릭터에 보상 부여

        return new LevelUpResponse(level.get());
    }
}
