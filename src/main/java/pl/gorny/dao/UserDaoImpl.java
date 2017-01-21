package pl.gorny.dao;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.gorny.model.User;
import pl.gorny.utils.Queries;

@Transactional
@Repository("userDao")
public class UserDaoImpl extends AbstractDao implements UserDao {

    @Override
    public User findUserByEmail(String email) {
        Query query = getSession().createSQLQuery(Queries.GET_USER_BY_EMAIL).addEntity(User.class);
        query.setParameter("email", email);

        return (User) query.uniqueResult();
    }

    @Override
    public void saveUser(User user) {
        persist(user);
    }
}
