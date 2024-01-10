package com.project.game.skill.domain;

import static jakarta.persistence.FetchType.LAZY;

import com.project.game.common.domain.BaseEntity;
import com.project.game.common.domain.Stat;
import com.project.game.common.domain.StatRate;
import com.project.game.skill.vo.SkillEffectType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "skill_effect_type")
    private SkillEffectType skillEffectType;

    @Column(name = "mana_cost")
    private Integer manaCost;

    @Column(name = "fixed_value")
    private Integer fixedValue;

    @Embedded
    private StatRate statRate;

    /**
     * 고정 수치와 스킬 시전자의 Stat 비례 수치를 고려한 최종 스킬 효과 수치 반환
     * @param turnOwnerStat 
     * @param effect
     * @return
     */
    public static Integer makeEffectValue(Stat turnOwnerStat, SkillEffect effect) {
        double atkRelative = calculateRelativeValue(effect.getStatRate().getAtkRate(), turnOwnerStat.getAtk());
        double hpRelative = calculateRelativeValue(effect.getStatRate().getHpRate(), turnOwnerStat.getHp());
        double mpRelative = calculateRelativeValue(effect.getStatRate().getMpRate(), turnOwnerStat.getMp());
        double spdRelative = calculateRelativeValue(effect.getStatRate().getSpdRate(), turnOwnerStat.getSpd());

        int effectValue = effect.getFixedValue() + roundToInt(atkRelative) +
            roundToInt(hpRelative) + roundToInt(mpRelative) + roundToInt(spdRelative);

        return effectValue;
    }

    private static double calculateRelativeValue(int rate, int baseValue) {
        return (double) rate / 100 * baseValue;
    }

    private static int roundToInt(double value) {
        return (int) Math.round(value);
    }

}
