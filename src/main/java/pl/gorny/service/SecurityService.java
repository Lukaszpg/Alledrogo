package pl.gorny.service;

import pl.gorny.model.User;

public interface SecurityService {
    User getUserByEmail(String email);

    String findLoggedInUsername();

    boolean login(String username, String password);
}
