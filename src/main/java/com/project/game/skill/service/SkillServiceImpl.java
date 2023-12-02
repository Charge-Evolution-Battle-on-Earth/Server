package com.project.game.skill.service;

import com.project.game.nation.domain.Nation;
import com.project.game.nation.dto.NationGetListResponse;
import com.project.game.skill.domain.Skill;
import com.project.game.skill.dto.SkillGetListResponse;
import com.project.game.skill.repository.SkillRepository;
import com.project.game.skill.service.usecase.SkillService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.ConcreteCflowPointcut.Slot;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    @Override
    public SkillGetListResponse getSkillList() {
        List<Skill> skills = skillRepository.findAll();
        return new SkillGetListResponse(skills);
    }
}
