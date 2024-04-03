package com.project.game.skill.repository;

import com.project.game.skill.domain.Skill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findByNationIdAndLevelId(Long nationId, Long levelId);
}
