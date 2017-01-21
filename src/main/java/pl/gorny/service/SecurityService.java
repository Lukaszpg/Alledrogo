package pl.gorny.service;

import pl.gorny.model.User;

public interface SecurityService {
    User getUserByEmail(String email);

    String findLoggedInUsername();

    String findLoggedInRole();

    boolean login(String username, String password);
}
