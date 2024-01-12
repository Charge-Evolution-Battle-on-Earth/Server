package com.project.game.character.domain;

import static jakarta.persistence.FetchType.LAZY;
import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;

import com.project.game.character.exception.CostInvalidException;
import com.project.game.job.domain.Job;
import com.project.game.nation.domain.Nation;
import com.project.game.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "characters")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Character {

    @Transient
    private static final Integer INITIAL_EXP = 0;

    @Transient
    private static final Integer INITIAL_MONEY = 0;

    @Transient
    private static final Integer INITIAL_LEVEL = 1;

    @Id
    @Column(name = "character_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long characterId;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "level_id")
    private Integer levelId;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "nation_id")
    private Nation nation;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    private Integer money;

    private Integer exp;

    private String imageUrl;

    public void plusMoney(Integer cost){
        if(validateCost(cost)){
            throw new CostInvalidException(cost);
        }
        this.money += cost;
    }

    public void minusMoney(Integer cost){
        if(validateCost(cost) || this.money.compareTo(cost) == -1){
            throw new CostInvalidException(cost);
        }
        this.money -= cost;
    }

    public void plusExp(Integer value){
        this.exp += value;
    }

    public void minusExp(Integer value){
        this.exp -= value;
    }

    public void setLevel(Integer level){
        this.levelId = level;
    }

    private boolean validateCost(Integer cost) {
        return cost == null || cost < ZERO;
    }

    @Builder
    public Character(Long characterId, User user, Nation nation, Job job, String imageUrl) {
        this.characterId = characterId;
        this.user = user;
        this.levelId = INITIAL_LEVEL;
        this.nation = nation;
        this.job = job;
        this.money = INITIAL_MONEY;
        this.exp = INITIAL_EXP;
        this.imageUrl = imageUrl;
    }
}
