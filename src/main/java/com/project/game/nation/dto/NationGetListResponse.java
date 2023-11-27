package com.project.game.nation.dto;

import com.project.game.nation.domain.Nation;
import java.util.List;
import lombok.Getter;

@Getter
public class NationGetListResponse {

    private List<Nation> nations;

    public NationGetListResponse(List<Nation> nations) {
        this.nations = nations;
    }
}
