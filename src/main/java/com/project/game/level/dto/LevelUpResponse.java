package com.project.game.level.dto;

import com.project.game.level.domain.Level;
import lombok.Getter;

@Getter
public class LevelUpResponse {

    private Integer needExp;
    private Integer rewardGold;

    public LevelUpResponse(Level level) {
        this.needExp = level.getNeedExp();
        this.rewardGold = level.getRewardGold();
    }
}
