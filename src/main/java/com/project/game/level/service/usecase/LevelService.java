package com.project.game.level.service.usecase;

import com.project.game.level.dto.LevelUpResponse;

public interface LevelService {

    LevelUpResponse levelUp(Integer levelId);
}
