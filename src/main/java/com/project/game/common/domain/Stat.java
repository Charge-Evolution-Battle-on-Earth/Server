package com.project.game.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Stat {


    @Column(nullable = false)
    private Integer hp;

    @Column(nullable = false)
    private Integer atk;

    @Column(nullable = false)
    private Integer mp;

    @Column(nullable = false)
    private Integer spd;
}
