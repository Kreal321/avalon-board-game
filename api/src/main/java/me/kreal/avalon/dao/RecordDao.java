package me.kreal.avalon.dao;

import me.kreal.avalon.domain.Game;
import me.kreal.avalon.domain.Player;
import me.kreal.avalon.domain.Record;
import me.kreal.avalon.domain.User;
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
public class RecordDao extends BaseDao<Record> {

    @Autowired
    public RecordDao(SessionFactory factory) {
        super(Record.class, factory);
    }

    public Optional<Record> findRecordByGameAndUser(Game g, User u) {
        Session session = factory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Record> criteriaQuery = criteriaBuilder.createQuery(Record.class);
        Root<Record> root = criteriaQuery.from(Record.class);
        criteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.equal(root.get("game"), g),
                criteriaBuilder.equal(root.get("user"), u)
        ));
        return session.createQuery(criteriaQuery).getResultStream().findAny();
    }
}
