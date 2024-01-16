package com.project.game.match.domain;

import static jakarta.persistence.FetchType.LAZY;

import com.project.game.character.domain.Character;
import com.project.game.common.domain.BaseEntity;
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

@Entity(name = "match_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_history_id")
    private Long matchHistoryId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "match_room_id")
    private MatchRoom matchRoom;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "winner")
    private Character winner;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "loser")
    private Character loser;

    @Column(name = "staked_gold")
    private Integer stakedGold;

    @Builder
    public MatchHistory(MatchRoom matchRoom, Character winner, Character loser,
        Integer stakedGold) {
        this.matchRoom = matchRoom;
        this.winner = winner;
        this.loser = loser;
        this.stakedGold = stakedGold;
    }
}
