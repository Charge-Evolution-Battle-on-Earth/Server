package com.project.game.character.domain;

import com.project.game.common.domain.Stat;
import com.project.game.job.domain.Job;
import com.project.game.level.domain.Level;
import com.project.game.nation.domain.Nation;
import com.project.game.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private Integer exp;

    private String imageUrl;

    @Builder
    public Character(Long characterId, User user, Long levelId, Nation nation, Job job, Integer exp,
        String imageUrl) {
        this.characterId = characterId;
        this.user = user;
        this.levelId = levelId;
        this.nation = nation;
        this.job = job;
        this.exp = exp;
        this.imageUrl = imageUrl;
    }
}
