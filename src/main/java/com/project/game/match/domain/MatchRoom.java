package com.project.game.match.domain;

import static jakarta.persistence.FetchType.LAZY;

import com.project.game.character.domain.Character;
import com.project.game.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "match_room")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_room_id")
    private Long matchRoomId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "creator")
    private Character creator;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "entrant")
    private Character entrant;

    @Enumerated(EnumType.STRING)
    @Column(name = "match_status")
    private MatchStatus matchStatus;

    @Column(name = "staked_gold")
    private Integer stakedGold;

    @Builder
    public MatchRoom(Character creator, Character entrant, MatchStatus matchStatus,
        Integer stakedGold) {
        this.creator = creator;
        this.entrant = entrant;
        this.matchStatus = matchStatus;
        this.stakedGold = stakedGold;
    }
}
