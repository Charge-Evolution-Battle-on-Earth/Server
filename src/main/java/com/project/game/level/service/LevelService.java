package com.project.game.level.service;

import com.project.game.level.dto.LevelUpResponse;

public interface LevelService {

    LevelUpResponse levelUp(Integer levelId);
}
