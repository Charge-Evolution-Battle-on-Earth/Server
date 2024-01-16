package com.project.game.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCharacterUpsertRequest {

    private Long userId;
    private Long nationId;
    private Long jobId;
}
