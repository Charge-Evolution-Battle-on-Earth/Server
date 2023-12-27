package com.project.game.character.domain;

import com.project.game.common.domain.BaseEntity;
import com.project.game.item.domain.Item;
import com.project.game.item.domain.ItemType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "character_item_equip")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CharacterItemEquip extends BaseEntity {

    @Id
    @Column(name = "character_item_equip_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long characterItemEquipId;

    @ManyToOne
    @JoinColumn(name="character_id")
    private Character character;

    @ManyToOne
    @JoinColumn(name="item_type_id")
    private ItemType itemType;

    @ManyToOne
    @JoinColumn(name="character_item_id")
    private CharacterItem characterItem;

    @Builder
    public CharacterItemEquip(Character character, CharacterItem characterItem, ItemType itemType) {
        this.character = character;
        this.characterItem = characterItem;
        this.itemType = itemType;
    }
}
