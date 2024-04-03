package com.project.game.level.domain;

/**
 * 특정 Level 기준 스킬 획득 및 장비 구매를 위한 상수 데이터 관리
 */
public enum AccessLevel {
    LEVEL_ONE(1L),
    LEVEL_SEVEN(7L),
    LEVEL_TWELVE(12L);

    private final Long level;

    AccessLevel(Long level) {
        this.level = level;
    }

    public Long getLevel() {
        return level;
    }
}
