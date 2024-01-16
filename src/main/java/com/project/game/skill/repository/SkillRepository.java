package com.project.game.skill.repository;

import com.project.game.skill.domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SkillRepository extends JpaRepository<Skill, Long> {


    @Query(value =
        "select s.* from skills s "
            + "join nations n ON s.nation_id = n.nation_id "
            + "where s.nation_id = :nationId "
            + "and s.skill_nm = '타격'",
        nativeQuery = true)
    Skill findDefaultSkill(@Param("nationId") Long nationId);
}
