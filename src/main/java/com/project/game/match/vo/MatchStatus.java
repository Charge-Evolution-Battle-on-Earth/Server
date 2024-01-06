package com.project.game.match.vo;

import lombok.Getter;

/**
 * MatchRoom 내 매칭 상태 표현
 */
@Getter
public enum MatchStatus {

    WAITING,
    READY,
    IN_PROGRESS,
    FINISHED
}
