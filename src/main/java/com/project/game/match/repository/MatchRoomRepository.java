package com.project.game.match.repository;

import com.project.game.match.domain.MatchRoom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MatchRoomRepository extends JpaRepository<MatchRoom, Long> {

    @Query(
        value = "SELECT r.* FROM match_room r "
            + "ORDER BY "
            + "CASE r.match_status "
            + "WHEN 'WAITING' THEN 1 "
            + "WHEN 'READY' THEN 2 "
            + "WHEN 'IN_PROGRESS' THEN 3 "
            + "WHEN 'FINISHED' THEN 4 "
            + "ELSE 5 "
            + "END, "
            + "r.match_room_id ASC"
        , nativeQuery = true
    )
    Slice<MatchRoom> findAllByPaging(Pageable pageable);

}
