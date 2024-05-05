package com.project.game.item.repository;

import com.project.game.item.domain.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "SELECT * FROM items " +
        "WHERE item_type_id = :itemTypeId " +
        "AND level_id <= :maxLevelId " +
        "AND job_id = :jobId " +
        "ORDER BY level_id ASC, item_nm ASC", nativeQuery = true)
    List<Item> findItemByShop(
        @Param("itemTypeId") Long itemTypeId,
        @Param("maxLevelId") Long maxLevelId,
        @Param("jobId") Long jobId);
}
