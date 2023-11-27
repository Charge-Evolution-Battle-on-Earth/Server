package com.project.game.user.dto;

import com.project.game.user.domain.User;
import lombok.Getter;

@Getter
public class UserResponse {

    private Long userId;
    private String email;
    private String nickname;

    public UserResponse(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}