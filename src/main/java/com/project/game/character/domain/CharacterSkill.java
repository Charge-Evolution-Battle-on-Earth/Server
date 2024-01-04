package com.project.game.character.domain;

import static jakarta.persistence.FetchType.LAZY;

import com.project.game.common.domain.BaseEntity;
import com.project.game.item.domain.ItemType;
import com.project.game.skill.domain.Skill;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CharacterSkill extends BaseEntity {

    @Id
    @Column(name = "character_skill_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long characterSkillId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="character_id")
    private Character character;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="skill_id")
    private Skill skill;

    @Builder
    public CharacterSkill(Character character, Skill skill) {
        this.character = character;
        this.skill = skill;
    }
}
