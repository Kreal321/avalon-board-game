package me.kreal.avalon.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import me.kreal.avalon.domain.Game;
import me.kreal.avalon.domain.Player;
import me.kreal.avalon.domain.Team;
import me.kreal.avalon.domain.Vote;
import me.kreal.avalon.util.enums.RoundStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoundDTO {

    private Long roundId;
    private Long gameId;
    private Integer questNum;
    private Integer roundNum;
    private PlayerDTO leader;
    private Integer teamSize;
    private RoundStatus roundStatus;

    private List<VoteDTO> votes = new ArrayList<>();
    private List<TeamDTO> teams = new ArrayList<>();


}
