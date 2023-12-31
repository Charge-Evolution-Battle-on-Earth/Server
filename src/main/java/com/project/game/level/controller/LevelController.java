package com.project.game.level.controller;

import com.project.game.level.service.LevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/levels")
public class LevelController {

    private final LevelService levelService;
}
