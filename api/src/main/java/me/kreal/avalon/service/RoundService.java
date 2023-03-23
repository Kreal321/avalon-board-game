package me.kreal.avalon.service;

import me.kreal.avalon.dao.RoundDao;
import me.kreal.avalon.domain.*;
import me.kreal.avalon.dto.request.TeamRequest;
import me.kreal.avalon.dto.response.DataResponse;
import me.kreal.avalon.util.RoundMapper;
import me.kreal.avalon.util.avalon.GameModeFactory;
import me.kreal.avalon.util.enums.CharacterType;
import me.kreal.avalon.util.enums.RoundStatus;
import me.kreal.avalon.util.enums.TeamMemberStatus;
import me.kreal.avalon.util.enums.TeamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
        if (!roundOptional.isPresent()) {

            Round round = Round.builder()
                    .game(game)
                    .questNum(1)
                    .roundNum(1)
                    .leader(GameModeFactory.getGameMode(game.getGameMode()).getFirstLeader(game.getPlayers()))
                    .teamSize(GameModeFactory.getGameMode(game.getGameMode()).getTeamSize(1))
                    .roundStatus(RoundStatus.INITIAL_TEAM)
                    .votes(new ArrayList<>())
                    .teams(new ArrayList<>())
                    .build();

            this.roundDao.save(round);

            return round;
        }

        Round round = roundOptional.get();

        // game is over
        if (round.getQuestNum() == 5 && (round.getRoundNum() == 5 || round.getRoundStatus() == RoundStatus.QUEST_FAIL || round.getRoundStatus() == RoundStatus.QUEST_SUCCESS)) {
            return null;
        }

        // new round for current quest
        if (round.getRoundStatus() == RoundStatus.FINAL_TEAM_VOTING_FAIL && round.getRoundNum() < 5) {

            Round r = Round.builder()
                    .game(game)
                    .questNum(round.getQuestNum())
                    .roundNum(round.getRoundNum() + 1)
                    .leader(GameModeFactory.getGameMode(game.getGameMode()).getNextLeader(game.getPlayers(), round.getLeader()))
                    .teamSize(GameModeFactory.getGameMode(game.getGameMode()).getTeamSize(round.getQuestNum()))
                    .roundStatus(RoundStatus.INITIAL_TEAM)
                    .votes(new ArrayList<>())
                    .teams(new ArrayList<>())
                    .build();

            this.roundDao.save(r);

            return r;
        }

        // new round for next quest
        if ((round.getRoundStatus() == RoundStatus.QUEST_FAIL || round.getRoundStatus() == RoundStatus.QUEST_SUCCESS) && round.getQuestNum() < 5) {

            Round r = Round.builder()
                    .game(game)
                    .questNum(round.getQuestNum() + 1)
                    .roundNum(1)
                    .leader(GameModeFactory.getGameMode(game.getGameMode()).getNextLeader(game.getPlayers(), round.getLeader()))
                    .teamSize(GameModeFactory.getGameMode(game.getGameMode()).getTeamSize(round.getQuestNum() + 1))
                    .roundStatus(RoundStatus.INITIAL_TEAM)
                    .votes(new ArrayList<>())
                    .teams(new ArrayList<>())
                    .build();

            this.roundDao.save(r);

            return r;
        }

        return null;
    }

    @Transactional
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
                this.createNewRound(round.getGame());
            }
        }

        this.roundDao.save(round);


        return DataResponse.builder()
                .success(true)
                .message("Vote added")
                .data(RoundMapper.convertToResponse(round))
                .build();
    }

    @Transactional
    public DataResponse createNewChallengeForRound(Long roundId, Long gameId, Long playerId, Boolean success) {

        Optional<Round> roundOptional = this.roundDao.getById(roundId);

        if (!roundOptional.isPresent()) {
            return DataResponse.error("Round not found");
        }

        Round round = roundOptional.get();

        if (!round.getGame().getGameId().equals(gameId)) {
            return DataResponse.error("Round not found");
        }

        if (round.getRoundStatus() != RoundStatus.FINAL_TEAM_VOTING_SUCCESS) {
            return DataResponse.error("Round is not in pending challenge status");
        }

        Team finalTeam = round.getTeams().stream()
                .filter(team -> team.getTeamType() == TeamType.FINAL)
                .findAny()
                .get();

        Optional<TeamMember> teamMemberOptional = finalTeam.getTeamMembers().stream()
                .filter(member -> member.getPlayerId().equals(playerId))
                .findAny();

        if (!teamMemberOptional.isPresent()) {
            return DataResponse.error("You are not in the team");
        }

        TeamMember teamMember = teamMemberOptional.get();

        if (teamMember.getStatus() != TeamMemberStatus.CHALLENGE_PENDING) {
            return DataResponse.error("You have already voted");
        }

        if (!success && CharacterType.isGood(teamMember.getPlayer().getCharacterType())) {
            return DataResponse.error("You are a good character, you cannot fail the quest.");
        }

        teamMember.setStatus(success ? TeamMemberStatus.CHALLENGE_SUCCESS : TeamMemberStatus.CHALLENGE_FAILED);


        if (finalTeam.getTeamMembers().stream().noneMatch(member -> member.getStatus() == TeamMemberStatus.CHALLENGE_PENDING)) {
            if (finalTeam.getTeamMembers().stream().filter(member -> member.getStatus() == TeamMemberStatus.CHALLENGE_FAILED).count() >= GameModeFactory.getGameMode(round.getGame().getGameMode()).getFailThreshold(round.getQuestNum())) {
                round.setRoundStatus(RoundStatus.QUEST_FAIL);
            } else {
                round.setRoundStatus(RoundStatus.QUEST_SUCCESS);
            }
            this.createNewRound(round.getGame());
        }

        this.roundDao.save(round);

        return DataResponse.builder()
                .success(true)
                .message("Challenge added")
                .data(RoundMapper.convertToResponse(round))
                .build();
    }

    @Transactional
    public DataResponse createNewTeamForRound(Long roundId, Long gameId, Long playerId, TeamRequest teamRequest) {

        Optional<Round> roundOptional = this.roundDao.getById(roundId);

        if (!roundOptional.isPresent()) {
            return DataResponse.error("Round not found");
        }

        Round round = roundOptional.get();

        if (!round.getGame().getGameId().equals(gameId)) {
            return DataResponse.error("Round not found");
        }

        if (!round.getLeader().getPlayerId().equals(playerId)) {
            return DataResponse.error("You are not the leader");
        }

        if (teamRequest.getTeamType() == TeamType.INITIAL && round.getRoundStatus() != RoundStatus.INITIAL_TEAM) {
            return DataResponse.error("Round is not in initial team status");
        }

        if (teamRequest.getTeamType() == TeamType.FINAL && round.getRoundStatus() != RoundStatus.DISCUSSING) {
            return DataResponse.error("Round is not in final team status");
        }

        if (teamRequest.getTeamMembers().size() != round.getTeamSize()) {
            return DataResponse.error("Team size is not correct");
        }

        List<Long> playerIds = round.getGame().getPlayers().stream()
                .map(Player::getPlayerId)
                .collect(Collectors.toList());

        if (teamRequest.getTeamMembers().stream().filter(playerIds::contains).count() != round.getTeamSize()) {
            return DataResponse.error("Team members are not in the game");
        }

        Team team = Team.builder()
                .teamType(teamRequest.getTeamType())
                .teamMembers(new ArrayList<>())
                .build();

        team.addTeamMembers(teamRequest.getTeamMembers().stream()
                .map(id -> TeamMember.builder()
                        .playerId(id)
                        .status(TeamMemberStatus.CHALLENGE_PENDING)
                        .build())
                .collect(Collectors.toList()));

        round.addTeam(team);

        if (teamRequest.getTeamType() == TeamType.INITIAL) {
            round.setRoundStatus(RoundStatus.DISCUSSING);
        } else {
            if (round.getRoundNum() == 5) {
                round.setRoundStatus(RoundStatus.FINAL_TEAM_VOTING_SUCCESS);
            } else {
                round.setRoundStatus(RoundStatus.FINAL_TEAM_VOTING);
            }
        }

        this.roundDao.save(round);

        return DataResponse.builder()
                .success(true)
                .message("Team added")
                .data(RoundMapper.convertToResponse(round))
                .build();

    }
}
