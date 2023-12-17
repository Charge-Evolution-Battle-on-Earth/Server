package com.project.game.character.dto;

import com.project.game.character.domain.Character;
import com.project.game.job.domain.Job;
import com.project.game.level.domain.Level;
import com.project.game.nation.domain.Nation;
import com.project.game.user.domain.User;
import lombok.Getter;

@Getter
public class CharacterInfoGetResponse {

    private User user;

    private Long levelId;

    private Nation nation;

    private Job job;

    private Integer exp;

    private String imageUrl;

    public CharacterInfoGetResponse(Character character) {
        this.user = character.getUser();
        this.levelId = character.getLevelId();
        this.nation = character.getNation();
        this.job = character.getJob();
        this.exp = character.getExp();
        this.imageUrl = character.getImageUrl();
    }
}
