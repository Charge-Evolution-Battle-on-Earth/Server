package com.project.game.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StatRate {

    @Size(max = 100)
    @Column(nullable = false)
    private Integer hpRate;

    @Size(max = 100)
    @Column(nullable = false)
    private Integer atkRate;

    @Size(max = 100)
    @Column(nullable = false)
    private Integer mpRate;

    @Size(max = 100)
    @Column(nullable = false)
    private Integer spdRate;

    @Builder
    public StatRate(Integer hpRate, Integer atkRate, Integer mpRate, Integer spdRate) {
        this.hpRate = hpRate;
        this.atkRate = atkRate;
        this.mpRate = mpRate;
        this.spdRate = spdRate;
    }
}
