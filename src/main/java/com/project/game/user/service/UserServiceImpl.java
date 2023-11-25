package com.project.game.user.service;

import com.project.game.user.dto.UserResponse;
import com.project.game.user.entity.User;
import com.project.game.user.repository.UserRepository;
import com.project.game.user.service.usecase.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse findByUserId(Long userId) {
        User user = userRepository.findByUserId(userId);
        return new UserResponse(user);
    }
}
