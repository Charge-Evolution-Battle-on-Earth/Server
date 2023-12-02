package com.project.game.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Embeddable
@Getter
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
}
