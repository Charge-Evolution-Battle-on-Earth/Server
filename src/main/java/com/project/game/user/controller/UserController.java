package com.project.game.user.controller;

import com.project.game.user.dto.UserCharacterUpsertRequest;
import com.project.game.user.dto.UserCharacterUpsertResponse;
import com.project.game.user.dto.UserLoginRequest;
import com.project.game.user.dto.UserLoginResponse;
import com.project.game.user.dto.UserLogoutRequest;
import com.project.game.user.dto.UserResponse;
import com.project.game.user.dto.UserUpsertRequest;
import com.project.game.user.dto.UserUpsertResponse;
import com.project.game.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/join")
    private ResponseEntity<UserUpsertResponse> join(@RequestBody UserUpsertRequest dto) {
        UserUpsertResponse response = userService.join(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/character")
    private ResponseEntity<UserCharacterUpsertResponse> joinCharacter(@RequestBody UserCharacterUpsertRequest dto) {
        UserCharacterUpsertResponse response = userService.joinCharacter(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    private ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest dto) {
        UserLoginResponse response = userService.login(dto);
        return ResponseEntity.ok(response);
    }

    //TODO refresh token 적용 후 작업 예정
    @PostMapping("/logout")
    private ResponseEntity<Void> logout(HttpServletRequest request, @RequestBody UserLogoutRequest dto) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
