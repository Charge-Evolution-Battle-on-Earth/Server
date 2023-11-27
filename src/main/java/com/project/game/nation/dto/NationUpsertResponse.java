package com.project.game.nation.dto;

import com.project.game.common.domain.Stat;
import com.project.game.nation.domain.Nation;
import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class NationUpsertResponse {

    private Long nationId;
    private String nationNm;
    private BigDecimal levelStatFactor;
    private Stat stat;

    public NationUpsertResponse(Nation entity) {
        this.nationId = entity.getNationId();
        this.nationNm = entity.getNationNm();
        this.levelStatFactor = entity.getLevelStatFactor();
        this.stat = entity.getStat();
    }
}
