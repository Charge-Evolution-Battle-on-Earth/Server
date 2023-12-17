package com.project.game.character.controller;

import com.project.game.character.dto.CharacterInfoGetResponse;
import com.project.game.character.service.usecase.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/characters")
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping("/{characterId}")
    private ResponseEntity<CharacterInfoGetResponse> getCharacterInfo(@PathVariable Long characterId){
        CharacterInfoGetResponse response = characterService.getCharacterInfo(characterId);
        return ResponseEntity.ok(response);
    }
}
