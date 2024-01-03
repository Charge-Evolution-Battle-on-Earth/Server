package com.project.game.user.service;

import com.project.game.user.dto.UserLoginRequest;
import com.project.game.user.dto.UserLoginResponse;
import com.project.game.user.dto.UserResponse;
import com.project.game.user.dto.UserUpsertRequest;

public interface UserService {

    UserResponse findByUserId(Long userId);

    UserResponse join(UserUpsertRequest dto);

    UserLoginResponse login(UserLoginRequest dto);

    String generateAccessToken(Long userId);

    Long getCharacterIdByToken(String token, String secretKey);
}
