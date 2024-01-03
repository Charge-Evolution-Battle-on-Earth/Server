package com.project.game.match.service;

import com.project.game.match.repository.MatchRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService{

    private final MatchRoomRepository matchRoomRepository;
}
