package com.project.game.level.service;

import com.project.game.level.dto.LevelUpResponse;
import com.project.game.level.domain.Level;
import com.project.game.level.exception.LevelInvalidException;
import com.project.game.level.repository.LevelRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LevelServiceImpl implements LevelService {

    private final LevelRepository levelRepository;

    @Override
    public LevelUpResponse levelUp(Integer levelId) {
        Optional<Level> level = levelRepository.findById(levelId);

        if(level.isEmpty()){
            throw new LevelInvalidException(levelId);
        }
        
        //TODO 캐릭터에 보상 부여

        return new LevelUpResponse(level.get());
    }
}
