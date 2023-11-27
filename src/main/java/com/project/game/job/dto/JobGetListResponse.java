package com.project.game.job.dto;

import com.project.game.job.domain.Job;
import java.util.List;
import lombok.Getter;

@Getter
public class JobGetListResponse {

    private List<Job> jobs;

    public JobGetListResponse(List<Job> jobs) {
        this.jobs = jobs;
    }
}
