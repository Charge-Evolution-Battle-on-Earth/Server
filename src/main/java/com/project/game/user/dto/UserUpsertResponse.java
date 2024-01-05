package com.project.game.user.dto;

import com.project.game.user.domain.User;
import lombok.Getter;

@Getter
public class UserUpsertResponse {

    private Long userId;
    private String email;
    private String nickname;

    public UserUpsertResponse(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}