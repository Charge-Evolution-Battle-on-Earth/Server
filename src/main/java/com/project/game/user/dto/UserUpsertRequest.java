package com.project.game.user.dto;

import static com.project.game.common.util.ShaUtil.sha256Encode;

import com.project.game.user.domain.User;
import lombok.Getter;

@Getter
public class UserUpsertRequest {

    private String email;
    private String password;
    private String nickname;

    public static User toEntity(UserUpsertRequest dto){
        return User.builder()
            .email(dto.getEmail())
            .password(sha256Encode(dto.getPassword()))
            .nickname(dto.getNickname())
            .build();
    }
}
