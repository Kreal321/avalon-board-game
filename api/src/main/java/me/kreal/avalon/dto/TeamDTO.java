package me.kreal.avalon.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import me.kreal.avalon.domain.Round;
import me.kreal.avalon.domain.TeamMember;
import me.kreal.avalon.util.enums.TeamType;

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
public class TeamDTO {

    private Long teamId;
    private Long roundId;
    private TeamType teamType;

    private List<TeamMember> teamMembers = new ArrayList<>();


}
