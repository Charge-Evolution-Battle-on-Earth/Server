package com.project.game.job.repository;

import com.project.game.job.domain.Job;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job,Long> {


    Optional<Job> findByJobNm(String jobNm);

    List<Job> findAllByOrderByJobIdAsc();
}
