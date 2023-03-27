package me.kreal.avalon.dao;

import me.kreal.avalon.domain.Team;
import me.kreal.avalon.domain.Vote;
import me.kreal.avalon.util.enums.TeamType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class TeamDao extends BaseDao<Team> {

    @Autowired
    public TeamDao(SessionFactory factory) {
        super(Team.class, factory);
    }

    public Optional<Team> findTeamByRoundIdAndTeamType(Long roundId, TeamType teamType) {
        Session session = factory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Team> criteriaQuery = criteriaBuilder.createQuery(Team.class);
        Root<Team> root = criteriaQuery.from(Team.class);
        criteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.equal(root.get("roundId"), roundId),
                criteriaBuilder.equal(root.get("teamType"), teamType)
        ));
        return session.createQuery(criteriaQuery).getResultStream().findAny();

    }

}
