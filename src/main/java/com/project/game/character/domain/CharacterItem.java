package com.project.game.character.domain;

import com.project.game.common.domain.BaseEntity;
import com.project.game.item.domain.Item;
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

@Entity(name = "character_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CharacterItem extends BaseEntity {

    @Id
    @Column(name = "character_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long characterItemId;

    @ManyToOne
    @JoinColumn(name="character_id")
    private Character character;

    @ManyToOne
    @JoinColumn(name="item_id")
    private Item item;

    @Builder
    public CharacterItem(Character character, Item item) {
        this.character = character;
        this.item = item;
    }
}
