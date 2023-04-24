package me.kreal.avalon.util;

import me.kreal.avalon.domain.Round;
import me.kreal.avalon.dto.response.PlayerResponse;
import me.kreal.avalon.dto.response.RoundResponse;
import me.kreal.avalon.util.enums.RoundStatus;

import java.util.stream.Collectors;

public class RoundMapper {

    public static RoundResponse convertToResponse(Round round) {
        RoundResponse response = RoundResponse.builder()
                .roundId(round.getRoundId())
                .gameId(round.getGameId())
                .questNum(round.getQuestNum())
                .roundNum(round.getRoundNum())
                .leaderId(round.getLeader().getPlayerId())
                .leader(PlayerMapper.convertToResponse(round.getLeader()))
                .teamSize(round.getTeamSize())
                .roundStatus(round.getRoundStatus())
                .votes(round.getVotes().stream().map(VoteMapper::convertToResponse).collect(Collectors.toList()))
                .teams(round.getTeams().stream().map(TeamMapper::convertToResponse).collect(Collectors.toList()))
                .build();

        if (round.getRoundStatus() == RoundStatus.FINAL_TEAM_VOTING) {
            response.getVotes().forEach(vote -> vote.setAccept(null));
        }

        response.getLeader().setCharacterType(null);

        return response;
    }
}
