package me.kreal.avalon.service;

import me.kreal.avalon.dao.TeamDao;
import me.kreal.avalon.dao.VoteDao;
import me.kreal.avalon.domain.Team;
import me.kreal.avalon.domain.Vote;
import me.kreal.avalon.util.enums.TeamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {

    private final TeamDao teamDao;

    @Autowired
    public TeamService(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    public Optional<Team> findFinalTeamByRoundId(Long roundId) {
        assert roundId != null;
        return teamDao.findTeamByRoundIdAndTeamType(roundId, TeamType.FINAL);
    }

}
