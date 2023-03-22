package me.kreal.avalon.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import me.kreal.avalon.domain.Team;
import me.kreal.avalon.dto.PlayerDTO;
import me.kreal.avalon.util.enums.TeamMemberStatus;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamMemberResponse {

    private Long teamMemberId;
    private Long teamId;
    private Long playerId;
    private TeamMemberStatus status;

}
