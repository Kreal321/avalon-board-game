package me.kreal.avalon.service;

import me.kreal.avalon.dao.RoundDao;
import me.kreal.avalon.domain.*;
import me.kreal.avalon.dto.request.TeamRequest;
import me.kreal.avalon.dto.response.DataResponse;
import me.kreal.avalon.util.RoundMapper;
import me.kreal.avalon.util.enums.RoundStatus;
import me.kreal.avalon.util.enums.TeamMemberStatus;
import me.kreal.avalon.util.enums.TeamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoundService {

    private final RoundDao roundDao;

    @Autowired
    public RoundService(RoundDao roundDao) {
        this.roundDao = roundDao;
    }

    public Optional<Round> findMostRecentRound(Long gameId) {
        return this.roundDao.findMostRecentRoundByGameId(gameId);
    }

    public Optional<Round> findByGameId(Long gameId){
        return this.roundDao.getById(gameId);
    }

    public Round createNewRound(Game game) {

        Optional<Round> roundOptional = this.findMostRecentRound(game.getGameId());

        // 1st round
        if(!roundOptional.isPresent()) {

            Round round = Round.builder()
                    .game(game)
                    .questNum(1)
                    .roundNum(1)
                    .leader(game.getPlayers().get(0))
                    .teamSize(3)
                    .roundStatus(RoundStatus.INITIAL_TEAM)
                    .build();

            this.roundDao.save(round);

            return round;
        }

        return null;
    }

    public DataResponse createNewVoteForRound(Long roundId, Long gameId, Long playerId, Boolean accept) {

        Optional<Round> roundOptional = this.roundDao.getById(roundId);

        if (!roundOptional.isPresent()) {
            return DataResponse.error("Round not found");
        }

        Round round = roundOptional.get();

        if (!round.getGame().getGameId().equals(gameId)) {
            return DataResponse.error("Round not found");
        }

        if (round.getRoundStatus() != RoundStatus.FINAL_TEAM_VOTING) {
            return DataResponse.error("Round is not in voting status");
        }

        if (round.getVotes().stream().anyMatch(vote -> vote.getPlayerId().equals(playerId))) {
            return DataResponse.error("You have already voted");
        }

        round.addVote(Vote.builder()
                        .playerId(playerId)
                        .accept(accept)
                        .build());


        if (round.getVotes().size() == round.getGame().getGameSize()) {
            if (round.getVotes().stream().filter(Vote::getAccept).count() >= round.getVotes().size() / 2) {
                round.setRoundStatus(RoundStatus.FINAL_TEAM_VOTING_SUCCESS);
            } else {
                round.setRoundStatus(RoundStatus.FINAL_TEAM_VOTING_FAIL);
            }
        }

        this.roundDao.save(round);


        return DataResponse.builder()
                .success(true)
                .message("Vote added")
                .data(round)
                .build();
    }


    @Transactional
    public DataResponse createNewTeamForRound(Long roundId, Long gameId, Long playerId, TeamRequest teamRequest) {

        Optional<Round> roundOptional = this.roundDao.getById(roundId);

        if (!roundOptional.isPresent()) {
            return DataResponse.error("Round not found");
        }

        Round round = roundOptional.get();

        System.out.print(round.getGame().getPlayers().get(1).getDisplayName());

        if (!round.getGame().getGameId().equals(gameId)) {
            return DataResponse.error("Round not found");
        }

        if (!round.getLeader().getPlayerId().equals(playerId)) {
            return DataResponse.error("You are not the leader");
        }

//        if (teamRequest.getTeamType() == TeamType.INITIAL && round.getRoundStatus() != RoundStatus.INITIAL_TEAM) {
//            return DataResponse.error("Round is not in initial team status");
//        }

        if (teamRequest.getTeamType() == TeamType.FINAL && round.getRoundStatus() != RoundStatus.DISCUSSING) {
            return DataResponse.error("Round is not in final team status");
        }

        if (teamRequest.getTeamMembers().size() != round.getTeamSize()) {
            return DataResponse.error("Team size is not correct");
        }

        Team team = Team.builder()
                .teamType(teamRequest.getTeamType())
                .teamMembers(new ArrayList<>())
                .build();

        team.addTeamMembers(teamRequest.getTeamMembers().stream()
                .map(id -> TeamMember.builder()
                        .playerId(id)
                        .status(TeamMemberStatus.VOTING_NOT_YET)
                        .build())
                .collect(Collectors.toList()));

        round.addTeam(team);

        if (teamRequest.getTeamType() == TeamType.INITIAL) {
            round.setRoundStatus(RoundStatus.DISCUSSING);
        } else {
            round.setRoundStatus(RoundStatus.FINAL_TEAM_VOTING);
        }

        this.roundDao.save(round);

        return DataResponse.builder()
                .success(true)
                .message("Team added")
                .data(RoundMapper.convertToResponse(round))
                .build();

    }
}
