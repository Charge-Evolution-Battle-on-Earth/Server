package com.project.game.match.repository;

import com.project.game.match.domain.MatchRoom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MatchRoomRepository extends JpaRepository<MatchRoom, Long> {

    @Query(
        value =
            "select r.* from match_room r",
        nativeQuery = true
    )
    Slice<MatchRoom> findAllByPaging(Pageable pageable);
}
