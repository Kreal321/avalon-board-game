package me.kreal.avalon.dao;

import me.kreal.avalon.domain.Game;
import me.kreal.avalon.domain.Player;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public class PlayerDao extends BaseDao<Player> {

    @Autowired
    public PlayerDao(SessionFactory factory) {
        super(Player.class, factory);
    }

}
