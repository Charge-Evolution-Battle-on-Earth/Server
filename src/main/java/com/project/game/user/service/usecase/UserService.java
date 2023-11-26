package com.project.game.user.service.usecase;

import com.project.game.user.dto.UserLoginRequest;
import com.project.game.user.dto.UserLoginResponse;
import com.project.game.user.dto.UserResponse;
import com.project.game.user.dto.UserUpsertRequest;
import com.project.game.user.entity.User;
import com.project.game.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface UserService {

    UserResponse findByUserId(Long userId);

    UserResponse join(UserUpsertRequest dto);

    UserLoginResponse login(UserLoginRequest dto);

    String generateAccessToken(Long userId);
}
