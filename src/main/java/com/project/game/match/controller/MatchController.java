package com.project.game.match.controller;

import com.project.game.match.dto.MatchRoomEnterRequest;
import com.project.game.match.dto.MatchRoomEnterResponse;
import com.project.game.match.dto.MatchRoomGetResponse;
import com.project.game.match.dto.MatchRoomUpsertResponse;
import com.project.game.match.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;

    @GetMapping("/room")
    private ResponseEntity<Slice<MatchRoomGetResponse>> findMatchRoomList(Pageable pageable) {
        Slice<MatchRoomGetResponse> response = matchService.findMatchRoomList(pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/room")
    private ResponseEntity<MatchRoomUpsertResponse> saveMatchRoom(
        @AuthenticationPrincipal Long characterId) {
        MatchRoomUpsertResponse response = matchService.saveMatchRoom(characterId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/room/enter")
    private ResponseEntity<MatchRoomEnterResponse> matchRoomEnter(
        @AuthenticationPrincipal Long characterId,
        @RequestBody MatchRoomEnterRequest matchRoomEnterRequest) {
        MatchRoomEnterResponse response = matchService.enterMatchRoom(characterId,
            matchRoomEnterRequest);
        return ResponseEntity.ok(response);
    }
}
