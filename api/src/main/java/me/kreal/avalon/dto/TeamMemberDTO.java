package me.kreal.avalon.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import me.kreal.avalon.domain.Player;
import me.kreal.avalon.domain.Team;
import me.kreal.avalon.util.enums.TeamMemberStatus;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamMemberDTO {


    private Long teamMemberId;

    @JsonIgnore
    private Team team;

    private PlayerDTO player;
    private TeamMemberStatus status;

}
