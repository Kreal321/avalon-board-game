package me.kreal.avalon.service;

import me.kreal.avalon.dao.PlayerDao;
import me.kreal.avalon.domain.Game;
import me.kreal.avalon.domain.Player;
import me.kreal.avalon.domain.User;
import me.kreal.avalon.security.AuthUserDetail;
import me.kreal.avalon.util.PlayerMapper;
import me.kreal.avalon.util.enums.CharacterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    public Optional<Player> findPlayerById(Long playerId) {
        return this.playerDao.getById(playerId);
    }

    public Player findPlayerByAuthUserDetail(AuthUserDetail authUserDetail) {
        return this.findPlayerById(authUserDetail.getPlayerId()).get();
    }

    @Transactional
    public Player assassinatePlayer(Player target) {
        target.setIsAssassinated(true);
        this.playerDao.update(target);
        return target;
    }

    @Transactional
    public Player playerRename(Player player, String newName) {
        player.setDisplayName(newName);
        this.playerDao.update(player);
        return player;
    }

    @Transactional
    public Player playerChangeCharacter(Player player, CharacterType characterType) {
        player.setCharacterType(characterType);
        this.playerDao.update(player);
        return player;
    }


}
