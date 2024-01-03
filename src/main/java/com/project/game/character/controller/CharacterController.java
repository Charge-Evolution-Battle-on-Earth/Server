package com.project.game.character.controller;

import com.project.game.character.dto.CharacterInfoGetResponse;
import com.project.game.character.dto.CharacterSkillGetResponse;
import com.project.game.character.service.CharacterService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/characters")
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping
    private ResponseEntity<CharacterInfoGetResponse> getCharacterInfo(@AuthenticationPrincipal Long characterId){
        CharacterInfoGetResponse response = characterService.getCharacterInfo(characterId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/skills")
    private ResponseEntity<List<CharacterSkillGetResponse>> getCharacterSkill(@AuthenticationPrincipal Long characterId){
        List<CharacterSkillGetResponse> response = characterService.getCharacterSkills(characterId);
        return ResponseEntity.ok(response);
    }

}
