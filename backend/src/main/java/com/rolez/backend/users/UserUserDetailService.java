package com.rolez.backend.users;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserUserDetailService implements UserDetailsService {

    private final UserDao userDao;

    public UserUserDetailService(@Qualifier("jpa") UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.selectUserByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException(
                        "Username " + username + " not found"
                ));
    }
}
