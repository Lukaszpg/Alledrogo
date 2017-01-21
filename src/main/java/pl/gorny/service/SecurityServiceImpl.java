package pl.gorny.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.gorny.dao.UserDao;
import pl.gorny.model.User;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public String findLoggedInUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        pl.gorny.model.User user = userDao.findUserByEmail(auth.getName());
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(user.getName());
        stringBuilder.append(" ");
        stringBuilder.append(user.getSurname());

        return stringBuilder.toString();
    }

    public String findLoggedInRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        pl.gorny.model.User user = userDao.findUserByEmail(auth.getName());

        return user.getRole().name();
    }

    @Override
    public boolean login(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails != null) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

            authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            if (usernamePasswordAuthenticationToken.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                return true;
            }

            return false;
        }

        return false;
    }
}