package com.project.game.job.dto;

import com.project.game.common.domain.Stat;
import com.project.game.job.domain.Job;
import com.project.game.nation.domain.Nation;
import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class JobUpsertResponse {

    private Long jobId;
    private String jobNm;
    private BigDecimal levelStatFactor;
    private Stat stat;

    public JobUpsertResponse(Job entity) {
        this.jobId = entity.getJobId();
        this.jobNm = entity.getJobNm();
        this.levelStatFactor = entity.getLevelStatFactor();
        this.stat = entity.getStat();
    }
}
