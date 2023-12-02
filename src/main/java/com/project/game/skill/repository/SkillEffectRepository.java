package com.project.game.skill.repository;

import com.project.game.skill.domain.SkillEffect;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillEffectRepository extends JpaRepository<SkillEffect, Long> {


    List<SkillEffect> findBySkillSkillId(Long skillId);
}
