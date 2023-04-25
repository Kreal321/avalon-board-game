package me.kreal.avalon.service;

import me.kreal.avalon.dao.RoundDao;
import me.kreal.avalon.domain.*;
import me.kreal.avalon.dto.request.TeamRequest;
import me.kreal.avalon.dto.response.DataResponse;
import me.kreal.avalon.util.RoundMapper;
import me.kreal.avalon.util.avalon.GameModeFactory;
import me.kreal.avalon.util.enums.*;
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

    public Optional<Round> findRoundById(Long roundId){
        assert roundId != null;
        return this.roundDao.getById(roundId);
    }

    public Round createNewRound(Game game) {

        // game is over
        if (GameStatus.gameIsFinished(game.getGameStatus())) {
            return null;
        }

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
    public void createNewTeamForRound(Round round, TeamType teamType, List<Player> teamMembers) {

        Team team = Team.builder()
                .teamType(teamType)
                .teamMembers(new ArrayList<>())
                .build();

        team.addTeamMembers(teamMembers.stream()
                .map(player -> TeamMember.builder()
                        .player(player)
                        .status(TeamMemberStatus.MISSION_PENDING)
                        .build())
                .collect(Collectors.toList()));

        round.addTeam(team);

        if (teamType == TeamType.INITIAL) {
            round.setRoundStatus(RoundStatus.DISCUSSING);
        } else {
            if (round.getRoundNum() == 5) {
                round.setRoundStatus(RoundStatus.FINAL_TEAM_VOTING_SUCCESS);
            } else {
                round.setRoundStatus(RoundStatus.FINAL_TEAM_VOTING);
            }
        }

        this.roundDao.save(round);

    }

    @Transactional
    public void createNewVoteForRound(Round round, Long playerId, Boolean accept) {

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
    }

    @Transactional
    public void createNewMissionForRound(Round round, Team finalTeam, TeamMember teamMember, boolean success) {

        teamMember.setStatus(success ? TeamMemberStatus.MISSION_SUCCESS : TeamMemberStatus.MISSION_FAILED);

        if (finalTeam.getTeamMembers().stream().noneMatch(member -> member.getStatus() == TeamMemberStatus.MISSION_PENDING)) {
            if (finalTeam.getTeamMembers().stream().filter(member -> member.getStatus() == TeamMemberStatus.MISSION_FAILED).count() >= GameModeFactory.getGameMode(round.getGame().getGameMode()).getFailThreshold(round.getQuestNum())) {
                round.setRoundStatus(RoundStatus.QUEST_FAIL);
            } else {
                round.setRoundStatus(RoundStatus.QUEST_SUCCESS);
            }
            this.createNewRound(round.getGame());
        }

        this.roundDao.save(round);
    }

}
