package com.project.game.skill.service;

import com.project.game.skill.dto.SkillGetListResponse;
import java.util.List;

public interface SkillService {

    List<SkillGetListResponse> getSkillList();
}
