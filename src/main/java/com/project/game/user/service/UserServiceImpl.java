package com.project.game.user.service;

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
    public User findByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }
}
