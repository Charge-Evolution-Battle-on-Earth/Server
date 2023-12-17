package com.project.game.skill.service;

import com.project.game.skill.domain.Skill;
import com.project.game.skill.dto.SkillGetListResponse;
import com.project.game.skill.repository.SkillRepository;
import com.project.game.skill.service.usecase.SkillService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    @Override
    public List<SkillGetListResponse> getSkillList() {
        List<Skill> skills = skillRepository.findAll();
        List<SkillGetListResponse> skillGetListResponseList = skills.stream().map(
            skill -> new SkillGetListResponse(skill)
        ).collect(Collectors.toList());
        return skillGetListResponseList;
    }
}
