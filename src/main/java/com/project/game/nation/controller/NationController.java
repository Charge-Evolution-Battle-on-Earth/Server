package com.project.game.nation.controller;

import com.project.game.nation.dto.NationGetListResponse;
import com.project.game.nation.dto.NationUpsertRequest;
import com.project.game.nation.dto.NationUpsertResponse;
import com.project.game.nation.service.usecase.NationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/nations")
public class NationController {

    private final NationService nationService;

    @GetMapping
    private ResponseEntity<NationGetListResponse> findAllNations() {
        NationGetListResponse response = nationService.getNationList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    private ResponseEntity<NationUpsertResponse> saveNation(@RequestBody NationUpsertRequest dto) {
        NationUpsertResponse response = nationService.saveNation(dto);
        return ResponseEntity.ok(response);
    }
}
