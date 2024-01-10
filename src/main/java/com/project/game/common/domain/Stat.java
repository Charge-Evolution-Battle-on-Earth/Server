package com.project.game.common.domain;

import static com.project.game.common.util.BigDecimalUtil.multiplyAndRound;

import com.project.game.common.exception.ValueInvalidException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
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

    public static Stat getMultipliedStat(Stat stat, BigDecimal factor){
        return new Stat(multiplyAndRound(stat.getHp(), factor), multiplyAndRound(stat.getAtk(),factor), multiplyAndRound(stat.getMp(),factor), multiplyAndRound(stat.getSpd(),factor));
    }

    public void plusStat(Stat stat){
        this.hp += stat.getHp();
        this.atk += stat.getAtk();
        this.mp += stat.getMp();
        this.spd += stat.getSpd();
    }

    public void minusHp(Integer value){
        this.hp -= value;
    }

    public void plusHpWithLimit(Integer limit, Integer value){
        if(this.hp + value > limit){
            this.hp = limit;
        }else{
            this.hp += value;
        }
    }

    public void minusMp(Integer value){
        if(this.mp < value){
            throw new ValueInvalidException();
        }
        this.mp -= value;
    }

    public Stat(Integer hp, Integer atk, Integer mp, Integer spd) {
        this.hp = hp;
        this.atk = atk;
        this.mp = mp;
        this.spd = spd;
    }

    public Stat() {
        this.hp = 0;
        this.atk = 0;
        this.mp = 0;
        this.spd = 0;
    }
}
