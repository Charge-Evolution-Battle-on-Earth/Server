package com.project.game.item.domain;

import com.project.game.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "item_type")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemType extends BaseEntity {

    @Id
    @Column(name = "item_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemTypeId;

    private String itemTypeNm;

    @Builder
    public ItemType(Long itemTypeId, String itemTypeNm) {
        this.itemTypeId = itemTypeId;
        this.itemTypeNm = itemTypeNm;
    }
}
