package com.project.game.user.service;

import static com.project.game.common.util.JwtUtil.createToken;
import static com.project.game.common.util.ShaUtil.sha256Encode;
import static com.project.game.user.dto.UserUpsertRequest.toEntity;

import com.project.game.user.dto.UserLoginRequest;
import com.project.game.user.dto.UserLoginResponse;
import com.project.game.user.dto.UserResponse;
import com.project.game.user.dto.UserUpsertRequest;
import com.project.game.user.entity.User;
import com.project.game.user.repository.UserRepository;
import com.project.game.user.service.usecase.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.access-expired-ms}")
    private Long accessExpiredMS;

    @Override
    public UserResponse findByUserId(Long userId) {
        User user = userRepository.findByUserId(userId);
        return new UserResponse(user);
    }

    @Override
    public UserResponse join(UserUpsertRequest dto) {
        //중복 체크
        Optional<User> user = userRepository.findByEmail(dto.getEmail());

        if(user.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "중복 아이디(이메일) 입니다.");
        }

        User savedUser = userRepository.save(toEntity(dto));
        return new UserResponse(savedUser);
    }

    @Override
    public UserLoginResponse login(UserLoginRequest dto) {
        Optional<User> loginUser = userRepository.findByEmailAndPassword(dto.getEmail(), sha256Encode(dto.getPassword()));

        if(loginUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "로그인 실패하였습니다.");
        }

        return new UserLoginResponse(generateAccessToken(loginUser.get().getUserId()));
    }

    @Override
    public String generateAccessToken(Long userId) {
        return createToken(
            (Claims) Jwts.claims().put("userId", userId),
            secretKey,
            accessExpiredMS
        );
    }
}
