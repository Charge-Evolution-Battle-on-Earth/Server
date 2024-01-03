package com.project.game.character.service;

import com.project.game.character.domain.Character;
import com.project.game.character.domain.CharacterItemEquip;
import com.project.game.character.domain.CharacterSkill;
import com.project.game.character.dto.CharacterInfoGetResponse;
import com.project.game.character.dto.CharacterSkillGetResponse;
import com.project.game.character.exception.CharacterNotFoundException;
import com.project.game.character.repository.CharacterItemEquipRepository;
import com.project.game.character.repository.CharacterRepository;
import com.project.game.character.repository.CharacterSkillRepository;
import com.project.game.common.domain.Stat;
import com.project.game.level.domain.Level;
import com.project.game.level.exception.LevelInvalidException;
import com.project.game.level.repository.LevelRepository;
import com.project.game.nation.domain.Nation;
import com.project.game.skill.repository.SkillRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final CharacterItemEquipRepository characterItemEquipRepository;
    private final LevelRepository levelRepository;
    private final CharacterSkillRepository characterSkillRepository;

    @Override
    public CharacterInfoGetResponse getCharacterInfo(Long characterId) {
        Character character = characterRepository.findById(characterId).orElseThrow(()->new CharacterNotFoundException(characterId));
        List<CharacterItemEquip> characterItemEquips = characterItemEquipRepository.findByCharacterCharacterId(characterId);
        Level level = levelRepository.findById(character.getLevelId()).orElseThrow(()->new LevelInvalidException());
        Stat totalStat = new Stat();

        //캐릭터 총 스탯 계산
        //나라 스탯 적용
        Stat nationStat = character.getNation().getStat();
        nationStat.multiplyStat(character.getNation().getLevelStatFactor());
        totalStat.addStat(nationStat);
        System.out.println(nationStat.getAtk());

        //직업 스탯 적용
        Stat jobStat = character.getJob().getStat();
        jobStat.multiplyStat(character.getJob().getLevelStatFactor());
        totalStat.addStat(jobStat);
        System.out.println(jobStat.getAtk());

        //착용 아이템 스탯 적용
        Stat totalItemStat = new Stat();
        for (CharacterItemEquip itemEquip : characterItemEquips) {
            Stat itemStat = itemEquip.getCharacterItem().getItem().getStat();
            totalItemStat.addStat(itemStat);
        }

        System.out.println(totalItemStat.getAtk());
        totalStat.addStat(totalItemStat);

        CharacterInfoGetResponse responseDTO = new CharacterInfoGetResponse(character, totalStat, level.getNeedExp());
        return responseDTO;
    }

    @Override
    public List<CharacterSkillGetResponse> getCharacterSkills(Long characterId) {
        List<CharacterSkill> characterSkills = characterSkillRepository.findByCharacterCharacterId(characterId);
        List<CharacterSkillGetResponse> responseDTO = characterSkills.stream().map(
            characterSkill -> new CharacterSkillGetResponse(characterSkill.getSkill())
        ).collect(Collectors.toList());
        return responseDTO;
    }
}
