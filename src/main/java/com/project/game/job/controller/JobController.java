package com.project.game.job.controller;

import com.project.game.job.dto.JobGetListResponse;
import com.project.game.job.dto.JobUpsertRequest;
import com.project.game.job.dto.JobUpsertResponse;
import com.project.game.job.service.usecase.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    @GetMapping
    private ResponseEntity<JobGetListResponse> findAllJobs() {
        JobGetListResponse response = jobService.getJobList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    private ResponseEntity<JobUpsertResponse> saveJob(@RequestBody JobUpsertRequest dto) {
        JobUpsertResponse response = jobService.saveJob(dto);
        return ResponseEntity.ok(response);
    }
}
