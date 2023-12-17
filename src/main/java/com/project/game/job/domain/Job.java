package com.project.game.job.domain;

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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Entity(name = "jobs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Job extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @Column(nullable = false)
    private String jobNm;

    @Column(precision = 6, scale = 3, nullable = false)
    private BigDecimal levelStatFactor;

    @Embedded
    private Stat stat;

    @Builder
    public Job(String jobNm, BigDecimal levelStatFactor, Stat stat) {
        this.jobNm = jobNm;
        this.levelStatFactor = levelStatFactor;
        this.stat = stat;
    }
}
