package com.project.game.skill.domain;

import static jakarta.persistence.FetchType.LAZY;

import com.project.game.common.domain.BaseEntity;
import com.project.game.common.domain.Stat;
import com.project.game.common.domain.StatRate;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "skill_effect")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SkillEffect extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillEffectId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "skill_id")
    private Skill skill;

    private Boolean target;

    private Integer turns;

    @Embedded
    private Stat stat;

    @Embedded
    private StatRate statRate;

}
