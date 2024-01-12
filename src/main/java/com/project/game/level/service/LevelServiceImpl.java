package com.project.game.level.service;

import com.project.game.character.domain.Character;
import com.project.game.character.exception.CharacterNotFoundException;
import com.project.game.character.repository.CharacterRepository;
import com.project.game.level.dto.LevelUpResponse;
import com.project.game.level.domain.Level;
import com.project.game.level.exception.LevelInvalidException;
import com.project.game.level.exception.LevelNotFoundException;
import com.project.game.level.repository.LevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LevelServiceImpl implements LevelService {

    private final LevelRepository levelRepository;
    private final CharacterRepository characterRepository;

    @Override
    public Boolean isLevelUp(Integer characterExp, Integer levelNeedExp) {
        if(characterExp >= levelNeedExp) return true;
        else return false;
    }

    /**
     * LevelUp 여부를 확인하고 처리하는 메서드
     * 다른 메서드에 의해 호출되는 메서드이므로 트랜잭션 전파 옵션 활성화 함.
     * @param characterId
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void checkAndProcessLevelUp(Long characterId) {
        Character character = characterRepository.findById(characterId)
            .orElseThrow(() -> new CharacterNotFoundException(characterId));

        Integer characterLevelId = character.getLevelId();
        Integer targetLevelId = ++characterLevelId;
        Level targetLevel = levelRepository.findById(targetLevelId)
        .orElseThrow(() -> new LevelNotFoundException(targetLevelId));

        if(isLevelUp(character.getExp(), targetLevel.getNeedExp())){
            //Level Up 적용 및 보상 부여
            character.setLevel(targetLevelId);
            character.plusMoney(targetLevel.getRewardGold());
            character.minusExp(targetLevel.getNeedExp());
            
            //TODO 추후 레벨 상승에 따른 스킬 습득 작업 시 처리할 위치
        }
    }
}
