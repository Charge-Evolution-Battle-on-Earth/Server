package com.project.game.match.domain;

import static com.project.game.match.vo.MatchStatus.WAITING;
import static com.project.game.match.vo.PlayerType.*;
import static com.project.game.skill.domain.SkillEffect.makeEffectValue;
import static com.project.game.skill.vo.SkillEffectType.DAMAGE;
import static com.project.game.skill.vo.SkillEffectType.HEAL;
import static jakarta.persistence.FetchType.LAZY;

import com.project.game.character.domain.Character;
import com.project.game.common.domain.BaseEntity;
import com.project.game.common.domain.Stat;
import com.project.game.match.exception.MatchRoomNotFinished;
import com.project.game.match.exception.PlayerTypeInvalidException;
import com.project.game.match.vo.MatchStatus;
import com.project.game.match.vo.PlayerType;
import com.project.game.skill.domain.SkillEffect;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
import org.springframework.beans.factory.annotation.Value;

@Entity(name = "match_room")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchRoom extends BaseEntity {


    @Transient
    private static final Integer loserGoldDivisor = 5;  //패배 시 패널티 비율 ex) 100(승리자 보상) / 5 = 20

    @Transient
    private static final Integer defaultStakedGold = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_room_id")
    private Long matchRoomId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "host")
    private Character host;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "entrant")
    private Character entrant;

    @Enumerated(EnumType.STRING)
    @Column(name = "match_status")
    private MatchStatus matchStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "turn_owner")
    private PlayerType turnOwner;

    @Column(name = "staked_gold")
    private Integer stakedGold;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "hp", column = @Column(name = "host_hp")),
        @AttributeOverride(name = "atk", column = @Column(name = "host_atk")),
        @AttributeOverride(name = "mp", column = @Column(name = "host_mp")),
        @AttributeOverride(name = "spd", column = @Column(name = "host_spd"))
    })
    private Stat hostStat;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "hp", column = @Column(name = "entrant_hp")),
        @AttributeOverride(name = "atk", column = @Column(name = "entrant_atk")),
        @AttributeOverride(name = "mp", column = @Column(name = "entrant_mp")),
        @AttributeOverride(name = "spd", column = @Column(name = "entrant_spd")),
    })
    private Stat entrantStat;

    @Column(name="host_start_hp")
    private Integer hostStartHp;

    @Column(name="entrant_start_hp")
    private Integer entrantStartHp;

    /**
     * 게임 승리 시 획득 Gold
     * 기본 금액 * level
     * @param level
     * @return
     */
    public static Integer makeStakedGold(Integer level){
        return defaultStakedGold * level;
    }

    public PlayerType getWinnerType(){
        Integer hostHp = this.hostStat.getHp();
        Integer entrantHp = this.entrantStat.getHp();

        if(hostHp > entrantHp && entrantHp <= 0){
            return HOST;
        }else if(hostHp < entrantHp && hostHp <= 0){
            return ENTRANT;
        }else{
            throw new MatchRoomNotFinished();
        }
    }

    public Integer getWinnerGold(Integer levelId){
        return this.stakedGold * levelId;
    }

    public Integer getLoserGold(Integer levelId){
        return this.stakedGold * levelId / loserGoldDivisor;
    }

    public Character getWinner(){
        Integer hostHp = this.hostStat.getHp();
        Integer entrantHp = this.entrantStat.getHp();

        if(hostHp > entrantHp && entrantHp <= 0){
            return this.host;
        }else if(hostHp < entrantHp && hostHp <= 0){
            return this.entrant;
        }else{
            throw new MatchRoomNotFinished();
        }
    }

    public Character getLoser(){
        Integer hostHp = this.hostStat.getHp();
        Integer entrantHp = this.entrantStat.getHp();

        if(hostHp < entrantHp && hostHp <= 0){
            return this.host;
        }else if(hostHp > entrantHp && entrantHp <= 0){
            return this.entrant;
        }else{
            throw new PlayerTypeInvalidException();
        }
    }

    public PlayerType getPlayerType(Long characterId){
        if(this.host.getCharacterId() == characterId) return HOST;
        else if(this.entrant.getCharacterId() == characterId) return ENTRANT;
        else return NONE;
    }

    public void effectSkillCast(Stat casterStat, Stat targetStat, Integer casterStartHp, SkillEffect effect) {
        int effectValue = makeEffectValue(casterStat, effect);

        casterStat.minusMp(effect.getManaCost());

        if (effect.getSkillEffectType() == DAMAGE) {
            targetStat.minusHp(effectValue);
        } else if (effect.getSkillEffectType() == HEAL) {
            casterStat.plusHpWithLimit(casterStartHp, effectValue);
        }
    }

    public void effectSkillCastByEntrant(SkillEffect effect) {
        effectSkillCast(entrantStat, hostStat, entrantStartHp, effect);
    }

    public void effectSkillCastByHost(SkillEffect effect) {
        effectSkillCast(hostStat, entrantStat, hostStartHp, effect);
    }

    public void setEntrant(Character entrant){
        this.entrant = entrant;
    }

    public void setHost(Character host){
        this.host = host;
    }

    public void setMatchStatus(MatchStatus matchStatus){
        this.matchStatus = matchStatus;
    }

    public void setTurnOwner(PlayerType turnOwner){
        this.turnOwner = turnOwner;
    }

    public void setHostStatAndStartHp(Stat hostStat){
        this.hostStat = hostStat;
        this.hostStartHp = hostStat.getHp();
    }

    public void setEntrantStatAndStartHp(Stat entrantStat){
        this.entrantStat = entrantStat;
        this.entrantStartHp = entrantStat.getHp();
    }

    public void setInitMatchRoom(){
        this.matchStatus = WAITING;
        this.turnOwner = NONE;
        setHostStatAndStartHp(new Stat());
        setEntrantStatAndStartHp(new Stat());
    }

    /**
     * 양 플레이어의 체력 상황을 체크하여 게임 종료 여부 확인
     * @return true: game over, false: game continue
     */
    public Boolean isGameOverWithStat(){
        int hostStatus = this.hostStat.getHp().compareTo(0);
        int entrantStatus = this.entrantStat.getHp().compareTo(0);

        if(hostStatus < 1 || entrantStatus < 1){
            return true;
        }else{
            return false;
        }
    }

    public PlayerType getToggleTurnOwner(){
        if(this.turnOwner == HOST){
            return ENTRANT;
        } else if (this.turnOwner == ENTRANT) {
            return HOST;
        }else{
            throw new PlayerTypeInvalidException();
        }
    }

    @Builder
    public MatchRoom(Character host, Character entrant, MatchStatus matchStatus,
        Integer stakedGold, PlayerType turnOwner) {
        this.host = host;
        this.entrant = entrant;
        this.matchStatus = matchStatus;
        this.stakedGold = stakedGold;
        this.turnOwner = turnOwner;
    }
}
