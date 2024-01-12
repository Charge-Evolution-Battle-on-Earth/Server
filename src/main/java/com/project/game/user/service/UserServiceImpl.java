package com.project.game.user.service;

import static com.project.game.common.util.JwtUtil.createToken;
import static com.project.game.common.util.JwtUtil.parse;
import static com.project.game.common.util.ShaUtil.sha256Encode;
import static com.project.game.user.dto.UserUpsertRequest.userUpsertToEntity;

import com.project.game.character.domain.Character;
import com.project.game.character.domain.CharacterSkill;
import com.project.game.character.repository.CharacterRepository;
import com.project.game.character.repository.CharacterSkillRepository;
import com.project.game.job.domain.Job;
import com.project.game.job.exception.JobNotFoundException;
import com.project.game.job.repository.JobRepository;
import com.project.game.nation.domain.Nation;
import com.project.game.nation.exception.NationNotFoundException;
import com.project.game.nation.repository.NationRepository;
import com.project.game.skill.domain.Skill;
import com.project.game.skill.repository.SkillRepository;
import com.project.game.user.dto.UserCharacterUpsertRequest;
import com.project.game.user.dto.UserCharacterUpsertResponse;
import com.project.game.user.dto.UserLoginRequest;
import com.project.game.user.dto.UserLoginResponse;
import com.project.game.user.dto.UserResponse;
import com.project.game.user.dto.UserUpsertRequest;
import com.project.game.user.domain.User;
import com.project.game.user.dto.UserUpsertResponse;
import com.project.game.user.exception.UserInvalidException;
import com.project.game.user.exception.UserNotFoundException;
import com.project.game.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CharacterRepository characterRepository;
    private final CharacterSkillRepository characterSkillRepository;
    private final SkillRepository skillRepository;
    private final JobRepository jobRepository;
    private final NationRepository nationRepository;

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
    public UserUpsertResponse join(UserUpsertRequest dto) {
        //중복 체크
        Optional<User> user = userRepository.findByEmail(dto.getEmail());

        if(user.isPresent()){
            throw new UserInvalidException();
        }

        User savedUser = userRepository.save(userUpsertToEntity(dto));
        return new UserUpsertResponse(savedUser);
    }

    @Override
    public UserLoginResponse login(UserLoginRequest dto) {
        User loginUser = userRepository.findByEmailAndPassword(dto.getEmail(), sha256Encode(dto.getPassword())).orElseThrow(()-> new UserNotFoundException());

        Character character = characterRepository.findByUserUserId(loginUser.getUserId());

        return new UserLoginResponse(generateAccessToken(character.getCharacterId()));
    }

    @Override
    public String generateAccessToken(Long characterId) {
        Claims claims = Jwts.claims();
        claims.put("characterId",characterId);

        return createToken(
            claims,
            secretKey,
            accessExpiredMS
        );
    }

    @Override
    public Long getCharacterIdByToken(String token, String secretKey) {
        Claims claims = parse(token, secretKey);
        return claims.get("characterId", Long.class);
    }

    @Override
    public UserCharacterUpsertResponse joinCharacter(UserCharacterUpsertRequest dto) {
        User user = userRepository.findByUserId(dto.getUserId());
        Job job = jobRepository.findById(dto.getJobId())
            .orElseThrow(()->new JobNotFoundException(dto.getJobId()));
        Nation nation = nationRepository.findById(dto.getNationId())
            .orElseThrow(() -> new NationNotFoundException(dto.getNationId()));

        Character character = Character.builder()
            .user(user)
            .nation(nation)
            .job(job)
            .build();

        Character savedCharacter = characterRepository.saveAndFlush(character);

        //TODO 계정 생성 시 나라별 스킬 2개를 추가로 배우도록 수정
        Skill defaultSkill = skillRepository.findDefaultSkill(character.getNation().getNationId());
        CharacterSkill characterSkill = CharacterSkill.builder().character(savedCharacter)
            .skill(defaultSkill).build();

        characterSkillRepository.save(characterSkill);

        return new UserCharacterUpsertResponse(savedCharacter, generateAccessToken(
            savedCharacter.getCharacterId()));
    }
}
