package com.project.game.job.service;

import com.project.game.job.dto.JobGetListResponse;
import com.project.game.job.dto.JobUpsertRequest;
import com.project.game.job.dto.JobUpsertResponse;
import java.util.List;

public interface JobService {

    List<JobGetListResponse> getJobList();

    JobUpsertResponse saveJob(JobUpsertRequest dto);
}
