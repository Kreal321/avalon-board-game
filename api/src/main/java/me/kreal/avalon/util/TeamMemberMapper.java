package me.kreal.avalon.util;

import me.kreal.avalon.domain.TeamMember;
import me.kreal.avalon.dto.response.TeamMemberResponse;

public class TeamMemberMapper {
    public static TeamMemberResponse convertToResponse(TeamMember teamMember) {
        return TeamMemberResponse.builder()
                .teamMemberId(teamMember.getTeamMemberId())
                .teamId(teamMember.getTeam().getTeamId())
                .playerId(teamMember.getPlayerId())
                .status(teamMember.getStatus())
                .build();
    }
}
