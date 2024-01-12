package com.project.game.level.service;

import com.project.game.level.dto.LevelUpResponse;

public interface LevelService {

    Boolean isLevelUp(Integer characterExp, Integer levelNeedExp);
    void checkAndProcessLevelUp(Long characterId);
}
