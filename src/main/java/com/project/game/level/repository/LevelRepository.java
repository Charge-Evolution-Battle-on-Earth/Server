package com.project.game.level.repository;

import com.project.game.level.domain.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository extends JpaRepository<Level, Integer> {


}
