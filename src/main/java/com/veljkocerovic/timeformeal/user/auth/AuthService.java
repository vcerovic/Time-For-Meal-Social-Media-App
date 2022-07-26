package com.veljkocerovic.timeformeal.user.auth;


import com.veljkocerovic.timeformeal.user.appuser.AppUser;
import com.veljkocerovic.timeformeal.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Load user from database
        Optional<AppUser> optionalUser = userRepository.findByUsername(username);
        AppUser appUser = optionalUser.orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        //Return spring user
        return new User(appUser.getUsername(),appUser.getPassword(),new ArrayList<>());
    }
}
