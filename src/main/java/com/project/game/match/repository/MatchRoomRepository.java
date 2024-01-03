package com.project.game.match.repository;

import com.project.game.match.domain.MatchRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRoomRepository extends JpaRepository<MatchRoom, Long> {
}
