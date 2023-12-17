package com.project.game.nation.dto;

import com.project.game.common.domain.Stat;
import com.project.game.nation.domain.Nation;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;

@Getter
public class NationGetListResponse {

    private Long nationId;
    private String nationNm;
    private BigDecimal levelStatFactor;
    private Stat stat;

    public NationGetListResponse(Nation nation) {
        this.nationId = nation.getNationId();
        this.nationNm = nation.getNationNm();
        this.levelStatFactor = nation.getLevelStatFactor();
        this.stat = nation.getStat();
    }
}
