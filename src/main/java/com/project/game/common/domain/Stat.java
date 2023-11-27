package com.project.game.common.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Stat {

    private Integer hp;
    private Integer atk;
    private Integer mp;
    private Integer spd;
}
