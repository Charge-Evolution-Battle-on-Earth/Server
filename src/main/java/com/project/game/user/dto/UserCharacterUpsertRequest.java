package com.project.game.user.dto;

import static com.project.game.common.util.ShaUtil.sha256Encode;

import com.project.game.character.domain.Character;
import com.project.game.job.repository.JobRepository;
import com.project.game.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCharacterUpsertRequest {

    private Long userId;
    private Long nationId;
    private Long jobId;
}
