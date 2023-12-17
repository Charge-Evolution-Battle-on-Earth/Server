package com.project.game.item.domain;

import com.project.game.common.domain.BaseEntity;
import com.project.game.common.domain.Stat;
import com.project.game.job.domain.Job;
import com.project.game.level.domain.Level;
import com.project.game.nation.domain.Nation;
import com.project.game.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(name = "level_id")
    private Long levelId;

    @OneToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "item_type_id")
    private ItemType itemType;

    private String itemNm;

    private Integer cost;

    @Embedded
    private Stat stat;

    private String description;

    @Builder
    public Item(Long levelId, Job job, ItemType itemType, String itemNm, Integer cost, Stat stat,
        String description) {
        this.levelId = levelId;
        this.job = job;
        this.itemType = itemType;
        this.itemNm = itemNm;
        this.cost = cost;
        this.stat = stat;
        this.description = description;
    }
}