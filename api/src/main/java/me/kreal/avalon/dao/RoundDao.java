package me.kreal.avalon.dao;

import me.kreal.avalon.domain.Game;
import me.kreal.avalon.domain.Player;
import me.kreal.avalon.domain.Record;
import me.kreal.avalon.domain.Round;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class RoundDao extends BaseDao<Round> {

    @Autowired
    public RoundDao(SessionFactory factory) {
        super(Round.class, factory);
    }

    public Optional<Round> findMostRecentRoundByGameId(Long gameId) {
        Session session = factory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Round> criteriaQuery = criteriaBuilder.createQuery(Round.class);
        Root<Round> root = criteriaQuery.from(Round.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("gameId"), gameId))
                .orderBy(criteriaBuilder.desc(root.get("roundId")));
        return session.createQuery(criteriaQuery).setMaxResults(1).getResultStream().findAny();
    }

    public List<Round> findAllRoundByGameId(Long gameId) {
        return new ArrayList<>(this.getAllBy("gameId", gameId));
    }

}
