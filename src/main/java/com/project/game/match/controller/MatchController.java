package com.project.game.match.controller;

import com.project.game.match.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;
}
