package com.project.game.match.domain;

import static com.project.game.match.vo.PlayerType.*;
import static jakarta.persistence.FetchType.LAZY;

import com.project.game.character.domain.Character;
import com.project.game.common.domain.BaseEntity;
import com.project.game.match.vo.MatchStatus;
import com.project.game.match.vo.PlayerType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "match_room")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchRoom extends BaseEntity {

    @Transient
    private static final Integer defaultStakedGold = 100;

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

    /**
     * 게임 승리 시 획득 Gold
     * 기본 금액 * level
     * @param level
     * @return
     */
    public static Integer makeStakedGold(Integer level){
        return defaultStakedGold * level;
    }

    public void setEntrant(Character entrant){
        this.entrant = entrant;
    }

    public PlayerType getPlayerType(Long characterId){
        if(this.creator.getCharacterId() == characterId) return CREATOR;
        else if(this.entrant.getCharacterId() == characterId) return ENTRANT;
        else return NONE;
    }

    public void setMatchStatus(MatchStatus matchStatus){
        this.matchStatus = matchStatus;
    }

    @Builder
    public MatchRoom(Character creator, Character entrant, MatchStatus matchStatus,
        Integer stakedGold) {
        this.creator = creator;
        this.entrant = entrant;
        this.matchStatus = matchStatus;
        this.stakedGold = stakedGold;
    }
}
