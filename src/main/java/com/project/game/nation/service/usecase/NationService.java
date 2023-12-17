package com.project.game.nation.service.usecase;

import com.project.game.nation.dto.NationGetListResponse;
import com.project.game.nation.dto.NationUpsertRequest;
import com.project.game.nation.dto.NationUpsertResponse;
import java.util.List;

public interface NationService {

    List<NationGetListResponse> getNationList();

    NationUpsertResponse saveNation(NationUpsertRequest dto);
}
