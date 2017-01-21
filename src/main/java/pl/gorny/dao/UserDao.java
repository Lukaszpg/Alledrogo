package pl.gorny.dao;

import pl.gorny.model.User;

public interface UserDao {
    User findUserByEmail(String username);

    void saveUser(User user);
}
