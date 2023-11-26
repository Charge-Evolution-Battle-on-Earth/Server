package com.project.game.user.dto;

import com.project.game.common.util.ShaUtil;
import com.project.game.user.entity.User;
import lombok.Getter;

@Getter
public class UserLoginRequest {

    private String email;
    private String password;

}
