package com.project.game.job.service;

import static com.project.game.job.dto.JobUpsertRequest.toEntity;

import com.project.game.job.domain.Job;
import com.project.game.job.dto.JobGetListResponse;
import com.project.game.job.dto.JobUpsertRequest;
import com.project.game.job.dto.JobUpsertResponse;
import com.project.game.job.exception.JobInvalidException;
import com.project.game.job.repository.JobRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public List<JobGetListResponse> getJobList() {
        List<Job> jobs = jobRepository.findAllByOrderByJobIdAsc();
        List<JobGetListResponse> jobResponseList = jobs.stream().map(
            job -> new JobGetListResponse(job)
        ).collect(Collectors.toList());
        return jobResponseList;
    }

    @Override
    public JobUpsertResponse saveJob(JobUpsertRequest dto) {
        Optional<Job> findNation = jobRepository.findByJobNm(dto.getJobNm());

        if(findNation.isPresent()){
            throw new JobInvalidException();
        }
        Job job = jobRepository.save(toEntity(dto));
        return new JobUpsertResponse(job);
    }
}
