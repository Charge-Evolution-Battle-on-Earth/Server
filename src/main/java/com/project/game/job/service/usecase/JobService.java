package com.project.game.job.service.usecase;

import com.project.game.job.dto.JobGetListResponse;
import com.project.game.job.dto.JobUpsertRequest;
import com.project.game.job.dto.JobUpsertResponse;

public interface JobService {

    JobGetListResponse getJobList();

    JobUpsertResponse saveJob(JobUpsertRequest dto);
}
