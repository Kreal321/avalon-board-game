package me.kreal.avalon.service;

import me.kreal.avalon.dao.PlayerDao;
import me.kreal.avalon.dao.VoteDao;
import me.kreal.avalon.domain.Game;
import me.kreal.avalon.domain.Player;
import me.kreal.avalon.domain.User;
import me.kreal.avalon.domain.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService {

    private final VoteDao voteDao;

    @Autowired
    public VoteService(VoteDao voteDao) {
        this.voteDao = voteDao;
    }

    public Optional<Vote> findVoteByRoundIdAndPlayerId(Long roundId, Long playerId) {
        assert roundId != null && playerId != null;
        return voteDao.findVoteByRoundIdAndPlayerId(roundId, playerId);
    }

}
