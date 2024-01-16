package com.project.game.nation.repository;

import com.project.game.nation.domain.Nation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NationRepository extends JpaRepository<Nation, Long> {


    Optional<Nation> findByNationNm(String nationNm);

    List<Nation> findAllByOrderByNationIdAsc();
}
