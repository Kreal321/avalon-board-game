package me.kreal.avalon.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public abstract class BaseDao<T> {
    protected final SessionFactory factory;
    private final Class<T> clazz;

    @Autowired
    public BaseDao(Class<T> type, SessionFactory factory) {
        this.factory = factory;
        this.clazz = type;
    }

    public Long save(T t){
        Session session = factory.getCurrentSession();
        return (Long) session.save(t);
    }

    public void update(T t){
        Session session = factory.getCurrentSession();
        session.update(t);
    }

    public Optional<T> getById(Long id){
        Session session = factory.getCurrentSession();
        T object = session.get(clazz, id);
        return Optional.ofNullable(object);
    }

    public Set<T> getAll(){
        Session session = factory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = factory.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.select(root);
        Query<T> query = session.createQuery(criteriaQuery);
        return query.getResultStream().collect(Collectors.toSet());
    }

    public Set<T> getAllBy(String column, Object value) {
        Session session = factory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.where(criteriaBuilder.equal(root.get(column), value));
        return session.createQuery(criteriaQuery).getResultStream().collect(Collectors.toSet());
    }
}
