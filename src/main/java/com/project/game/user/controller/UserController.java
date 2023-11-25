package com.project.game.user.controller;

import com.project.game.user.dto.UserResponse;
import com.project.game.user.entity.User;
import com.project.game.user.service.usecase.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    private ResponseEntity<UserResponse> findById(@PathVariable Long userId) {
        UserResponse response = userService.findByUserId(userId);
        return ResponseEntity.ok(response);
    }
}
