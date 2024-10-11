package com.project.game.play.dto;

import com.project.game.character.domain.Character;
import lombok.Getter;

@Getter
public class PlayGreetingResponse {

    String greetingMessage;

    Long hostJobId;
    String hostJobNm;
    Long entrantJobId;
    String entrantJobNm;

    Long hostNationId;
    String hostNationNm;
    Long entrantNationId;
    String entrantNationNm;


    public PlayGreetingResponse(String characterNickname, Character host, Character entrant) {
        this.greetingMessage = "[" + characterNickname + "님이 입장하였습니다.]";
        if (host != null && host.getJob() != null) {
            this.hostJobId = host.getJob().getJobId();
            this.hostJobNm = host.getJob().getJobNm();
        }
        if (entrant != null && entrant.getJob() != null) {
            this.entrantJobId = entrant.getJob().getJobId();
            this.entrantJobNm = entrant.getJob().getJobNm();
        }
        if (host != null && host.getNation() != null) {
            this.hostNationId = host.getNation().getNationId();
            this.hostNationNm = host.getNation().getNationNm();
        }
        if (entrant != null && entrant.getNation() != null) {
            this.entrantNationId = entrant.getNation().getNationId();
            this.entrantNationNm = entrant.getNation().getNationNm();
        }
    }
}
