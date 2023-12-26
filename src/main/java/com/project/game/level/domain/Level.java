package com.project.game.level.domain;

import com.project.game.common.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "levels")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Level extends BaseEntity {

    @Id
    private Integer levelId;

    private Integer needExp;

    private Integer rewardGold;

    @Builder
    public Level(Integer levelId, Integer needExp, Integer rewardGold) {
        this.levelId = levelId;
        this.needExp = needExp;
        this.rewardGold = rewardGold;
    }
}
