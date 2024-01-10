package com.project.game.character.service;

import static com.project.game.common.domain.Stat.getMultipliedStat;

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
import java.util.List;
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
        Level level = levelRepository.findById(character.getLevelId()).orElseThrow(()->new LevelInvalidException());
        Stat totalStat = getCharacterTotalStat(character);

        CharacterInfoGetResponse responseDTO = new CharacterInfoGetResponse(character, totalStat, level.getNeedExp());
        return responseDTO;
    }

    /**
     * [Stat] Character 최종 스탯 반환 메서드
     * @param character 
     * @return
     */
    @Override
    public Stat getCharacterTotalStat(Character character) {
        List<CharacterItemEquip> characterItemEquips = characterItemEquipRepository.findByCharacterCharacterId(character.getCharacterId());
        Stat totalStat = new Stat();

        //캐릭터 총 스탯 계산
        //나라 스탯 적용
        Stat nationStat = character.getNation().getStat();
        nationStat = getMultipliedStat(nationStat, character.getNation().getLevelStatFactor());
        totalStat.plusStat(nationStat);

        //직업 스탯 적용
        Stat jobStat = character.getJob().getStat();
        jobStat = getMultipliedStat(jobStat, character.getJob().getLevelStatFactor());
        totalStat.plusStat(jobStat);

        //착용 아이템 스탯 적용
        Stat totalItemStat = new Stat();
        for (CharacterItemEquip itemEquip : characterItemEquips) {
            Stat itemStat = itemEquip.getCharacterItem().getItem().getStat();
            totalItemStat.plusStat(itemStat);
        }

        totalStat.plusStat(totalItemStat);
        return totalStat;
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
