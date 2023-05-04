package me.kreal.avalon.dao;

import me.kreal.avalon.domain.Player;
import me.kreal.avalon.domain.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDao extends BaseDao<User> {

    @Autowired
    public UserDao(SessionFactory factory) {
        super(User.class, factory);
    }

    public Optional<User> findUserByUsername(String username) {
        return this.getAllBy("username", username).stream().findFirst();
    }

    public Optional<User> findUserByEmail(String email) {
        return this.getAllBy("email", email).stream().findFirst();
    }
}
