package com.project.game.nation.dto;

import com.project.game.common.domain.Stat;
import com.project.game.nation.domain.Nation;
import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class NationUpsertRequest {

    private String nationNm;
    private BigDecimal levelStatFactor;
    private Stat stat;

    public static Nation toEntity(NationUpsertRequest dto){
        return new Nation(dto.getNationNm(),dto.getLevelStatFactor(),dto.getStat());
    }
}
