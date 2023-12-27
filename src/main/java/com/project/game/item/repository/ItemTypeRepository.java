package com.project.game.item.repository;

import com.project.game.item.domain.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {

}
