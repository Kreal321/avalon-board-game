package me.kreal.avalon.dao;

import me.kreal.avalon.domain.Game;
import me.kreal.avalon.util.enums.GameStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class GameDao extends BaseDao<Game> {

    @Autowired
    public GameDao(SessionFactory factory) {
        super(Game.class, factory);
    }

    public Optional<Game> findGameByGameNum(int gameNum) {
        return this.getAllBy("gameNum", gameNum).stream().findAny();
    }

    public Optional<Game> findGameByGameNumAndGameStatus(int gameNum, GameStatus gameStatus) {
        Session session = factory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Game> criteriaQuery = criteriaBuilder.createQuery(Game.class);
        Root<Game> root = criteriaQuery.from(Game.class);
        criteriaQuery.where(criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("gameNum"), gameNum),
                    criteriaBuilder.equal(root.get("gameStatus"), gameStatus)
                ));
        return session.createQuery(criteriaQuery).getResultStream().findAny();
    }

}
