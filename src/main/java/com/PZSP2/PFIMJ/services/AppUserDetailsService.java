package com.PZSP2.PFIMJ.services;
import java.util.Optional;

import com.PZSP2.PFIMJ.db.entities.User;
import com.PZSP2.PFIMJ.models.auth.AppUserDetails;
import com.PZSP2.PFIMJ.repositories.IUsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    IUsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(userName);

        if (!user.isEmpty()) {
            return user.map(AppUserDetails::new).get();
        } else {
            throw new UsernameNotFoundException("Not found: " + userName);
        }
    }
}

