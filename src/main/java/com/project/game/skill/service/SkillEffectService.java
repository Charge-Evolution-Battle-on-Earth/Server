package com.project.game.skill.service;

import com.project.game.skill.dto.SkillEffectGetListResponse;
import java.util.List;

public interface SkillEffectService {

    List<SkillEffectGetListResponse> getSkillEffectList(Long skillId);
}
