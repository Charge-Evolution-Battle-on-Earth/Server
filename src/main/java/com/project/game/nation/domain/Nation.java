package com.project.game.nation.domain;

import com.project.game.common.domain.BaseEntity;
import com.project.game.common.domain.Stat;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "nations")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nationId;
    private String nationNm;
    @Column(precision = 6, scale = 3)
    private BigDecimal levelStatFactor;

    @Embedded
    private Stat stat;

    public Nation(String nationNm, BigDecimal levelStatFactor, Stat stat) {
        this.nationNm = nationNm;
        this.levelStatFactor = levelStatFactor;
        this.stat = stat;
    }
}
