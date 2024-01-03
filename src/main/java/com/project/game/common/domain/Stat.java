package com.project.game.common.domain;

import static com.project.game.common.util.BigDecimalUtil.multiplyAndRound;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public void multiplyStat(BigDecimal factor){
        this.hp = multiplyAndRound(this.hp, factor);
        this.atk = multiplyAndRound(this.atk, factor);
        this.mp = multiplyAndRound(this.mp, factor);
        this.spd = multiplyAndRound(this.spd, factor);
    }

    public void addStat(Stat stat){
        this.hp += stat.getHp();
        this.atk += stat.getAtk();
        this.mp += stat.getMp();
        this.spd += stat.getSpd();
    }

    public Stat() {
        this.hp = 0;
        this.atk = 0;
        this.mp = 0;
        this.spd = 0;
    }
}
