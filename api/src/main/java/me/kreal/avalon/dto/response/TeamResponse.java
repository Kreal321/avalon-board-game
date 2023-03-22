package me.kreal.avalon.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import me.kreal.avalon.domain.TeamMember;
import me.kreal.avalon.util.enums.TeamType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamResponse {

    private Long teamId;
    private Long roundId;
    private TeamType teamType;

    private List<TeamMemberResponse> teamMembers = new ArrayList<>();


}
