package com.project.game.skill.service;

import com.project.game.skill.dto.SkillEffectGetListResponse;
import com.project.game.skill.dto.SkillEffectGetResponse;
import com.project.game.skill.dto.SkillEffectUpsertRequest;
import java.util.List;

public interface SkillEffectService {

    List<SkillEffectGetListResponse> getSkillEffectList(Long skillId);

    void update(SkillEffectUpsertRequest dto);

    List<SkillEffectGetResponse> getAllSkillEffectList();
}
