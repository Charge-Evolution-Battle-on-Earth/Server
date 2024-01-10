package com.project.game.item.repository;

import com.project.game.item.domain.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByItemTypeItemTypeIdAndLevelIdLessThanEqualAndJobJobId(Long itemTypeId, Long levelId, Long jobId);
}
