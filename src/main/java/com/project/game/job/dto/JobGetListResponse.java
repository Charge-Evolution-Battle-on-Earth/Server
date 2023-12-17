package com.project.game.job.dto;

import com.project.game.common.domain.Stat;
import com.project.game.job.domain.Job;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;

@Getter
public class JobGetListResponse {

    private Long jobId;
    private String jobNm;
    private BigDecimal levelStatFactor;
    private Stat stat;

    public JobGetListResponse(Job job) {
        this.jobId = job.getJobId();
        this.jobNm = job.getJobNm();
        this.levelStatFactor = job.getLevelStatFactor();
        this.stat = job.getStat();
    }
}
