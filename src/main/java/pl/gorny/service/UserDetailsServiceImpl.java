package pl.gorny.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.gorny.dao.UserDao;
import pl.gorny.model.User;

import java.util.HashSet;
import java.util.Set;

@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User optionalUser = userDao.findUserByEmail(username);

        if(optionalUser != null) {
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(optionalUser.getRole().name()));

            return new org.springframework.security.core.userdetails.User(optionalUser.getEmail(), optionalUser.getPassword(), grantedAuthorities);
        }

        return null;
    }
}
