package com.project.game.skill.service.usecase;

import com.project.game.skill.dto.SkillGetListResponse;
import java.util.List;

public interface SkillService {

    List<SkillGetListResponse> getSkillList();
}
