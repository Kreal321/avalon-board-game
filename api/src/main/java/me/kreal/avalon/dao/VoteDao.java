package me.kreal.avalon.dao;

import me.kreal.avalon.domain.Record;
import me.kreal.avalon.domain.User;
import me.kreal.avalon.domain.Vote;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class VoteDao extends BaseDao<Vote> {

    @Autowired
    public VoteDao(SessionFactory factory) {
        super(Vote.class, factory);
    }

    public Optional<Vote> findVoteByRoundIdAndPlayerId(Long roundId, Long playerId) {
        Session session = factory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Vote> criteriaQuery = criteriaBuilder.createQuery(Vote.class);
        Root<Vote> root = criteriaQuery.from(Vote.class);
        criteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.equal(root.get("roundId"), roundId),
                criteriaBuilder.equal(root.get("playerId"), playerId)
        ));
        return session.createQuery(criteriaQuery).getResultStream().findAny();

    }

}
