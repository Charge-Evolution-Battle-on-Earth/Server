package com.project.game.nation.service.usecase;

import com.project.game.nation.dto.NationGetListResponse;
import com.project.game.nation.dto.NationUpsertRequest;
import com.project.game.nation.dto.NationUpsertResponse;

public interface NationService {

    NationGetListResponse getNationList();

    NationUpsertResponse saveNation(NationUpsertRequest dto);
}
