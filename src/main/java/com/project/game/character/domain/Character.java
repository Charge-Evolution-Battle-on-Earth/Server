package com.project.game.character.domain;

import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;

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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Entity(name = "characters")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Character {

    @Id
    @Column(name = "character_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long characterId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "level_id")
    private Long levelId;

    @OneToOne
    @JoinColumn(name = "nation_id")
    private Nation nation;

    @OneToOne
    @JoinColumn(name = "job_id")
    private Job job;

    private Integer money;

    private Integer exp;

    private String imageUrl;

    public void plusMoney(Integer cost){
        if(validateCost(cost)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 Cost 입력입니다.");
        }
        this.money += cost;
    }

    public void minusMoney(Integer cost){
        if(validateCost(cost) || this.money.compareTo(cost) == -1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 Cost 입력입니다.");
        }
        this.money -= cost;
    }

    private boolean validateCost(Integer cost) {
        return cost == null || cost < ZERO;
    }

    @Builder
    public Character(Long characterId, User user, Long levelId, Nation nation, Job job, Integer money, Integer exp,
        String imageUrl) {
        this.characterId = characterId;
        this.user = user;
        this.levelId = levelId;
        this.nation = nation;
        this.job = job;
        this.money = money;
        this.exp = exp;
        this.imageUrl = imageUrl;
    }
}
