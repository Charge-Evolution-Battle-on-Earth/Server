package com.project.game.character.dto;

import com.project.game.character.domain.Character;
import com.project.game.common.domain.Stat;
import com.project.game.job.domain.Job;
import com.project.game.nation.domain.Nation;
import lombok.Getter;

@Getter
public class CharacterInfoGetResponse {

    private Long characterId;
    private String nickname;
    private Stat stat;
    private Integer levelId;
    private Integer currentExp;
    private Integer totalExp;
    private Long nationId;
    private String nationNm;
    private Long jobId;
    private String jobNm;
    private Integer money;
    private String imageUrl;

    public CharacterInfoGetResponse(Character character, Stat stat, Integer totalExp) {
        Nation nation = character.getNation();
        Job job = character.getJob();
        this.characterId = character.getCharacterId();
        this.nickname = character.getUser().getNickname();
        this.stat = stat;
        this.levelId = character.getLevelId();
        this.currentExp = character.getExp();
        this.totalExp = totalExp;
        this.nationId = nation.getNationId();
        this.nationNm = nation.getNationNm();
        this.jobId = job.getJobId();
        this.jobNm = job.getJobNm();
        this.money = character.getMoney();
        this.imageUrl = character.getImageUrl();
    }
}
