package me.kreal.avalon.util;

import me.kreal.avalon.domain.Team;
import me.kreal.avalon.dto.response.TeamResponse;

import java.util.stream.Collectors;

public class TeamMapper {
    public static TeamResponse convertToResponse(Team team) {
        return TeamResponse.builder()
                .teamId(team.getTeamId())
                .roundId(team.getRound().getRoundId())
                .teamType(team.getTeamType())
                .teamMembers(team.getTeamMembers().stream().map(TeamMemberMapper::convertToResponse).collect(Collectors.toList()))
                .build();
    }
}
