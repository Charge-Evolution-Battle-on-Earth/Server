package com.project.game.job.dto;

import com.project.game.common.domain.Stat;
import com.project.game.job.domain.Job;
import com.project.game.nation.domain.Nation;
import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class JobUpsertRequest {

    private String jobNm;
    private BigDecimal levelStatFactor;
    private Stat stat;

    public static Job toEntity(JobUpsertRequest dto){
        return new Job(dto.getJobNm(),dto.getLevelStatFactor(),dto.getStat());
    }
}
