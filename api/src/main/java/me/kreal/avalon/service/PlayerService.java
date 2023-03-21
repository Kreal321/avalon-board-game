package me.kreal.avalon.service;

import me.kreal.avalon.dao.PlayerDao;
import me.kreal.avalon.domain.Game;
import me.kreal.avalon.domain.Player;
import me.kreal.avalon.domain.User;
import me.kreal.avalon.util.PlayerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final PlayerDao playerDao;

    @Autowired
    public PlayerService(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public Player createNewPlayer(Game g, User u) {
        Player p = Player.builder()
                .game(g)
                .displayName(u.getPreferredName())
                .build();

        this.playerDao.save(p);

        return p;
    }


}
