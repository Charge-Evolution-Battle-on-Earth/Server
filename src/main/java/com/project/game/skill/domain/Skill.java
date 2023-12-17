package com.project.game.skill.domain;

import com.project.game.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "skills")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Skill extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillId;

    @Column(nullable = false)
    private Long levelId;

    @Column(nullable = false)
    private Long nationId;

    @Column(nullable = false)
    private String skillNm;

    private String description;

    @Builder
    public Skill(Long levelId, Long nationId, String skillNm, String description) {
        this.levelId = levelId;
        this.nationId = nationId;
        this.skillNm = skillNm;
        this.description = description;
    }
}
