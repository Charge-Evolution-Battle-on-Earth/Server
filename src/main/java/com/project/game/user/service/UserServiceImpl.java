package com.project.game.user.service;

import static com.project.game.common.util.JwtUtil.createToken;
import static com.project.game.common.util.JwtUtil.parse;
import static com.project.game.common.util.ShaUtil.sha256Encode;
import static com.project.game.user.dto.UserUpsertRequest.toEntity;

import com.project.game.common.util.JwtUtil;
import com.project.game.user.dto.UserLoginRequest;
import com.project.game.user.dto.UserLoginResponse;
import com.project.game.user.dto.UserResponse;
import com.project.game.user.dto.UserUpsertRequest;
import com.project.game.user.domain.User;
import com.project.game.user.exception.UserInvalidException;
import com.project.game.user.exception.UserNotFoundException;
import com.project.game.user.repository.UserRepository;
import com.project.game.user.service.usecase.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Map;
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
            throw new UserInvalidException();
        }

        User savedUser = userRepository.save(toEntity(dto));
        return new UserResponse(savedUser);
    }

    @Override
    public UserLoginResponse login(UserLoginRequest dto) {
        Optional<User> loginUser = userRepository.findByEmailAndPassword(dto.getEmail(), sha256Encode(dto.getPassword()));

        if(loginUser.isEmpty()){
            throw new UserNotFoundException();
        }

        return new UserLoginResponse(generateAccessToken(loginUser.get().getUserId()));
    }

    @Override
    public String generateAccessToken(Long userId) {
        Claims claims = Jwts.claims();
        claims.put("userId",userId);

        return createToken(
            claims,
            secretKey,
            accessExpiredMS
        );
    }

    @Override
    public Long getUserIdByToken(String token, String secretKey) {
        Claims claims = parse(token, secretKey);
        return claims.get("userId", Long.class);
    }
}
