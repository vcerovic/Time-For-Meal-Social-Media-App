package com.veljkocerovic.timeformeal.user.auth;

import com.veljkocerovic.timeformeal.user.appuser.AppUser;
import com.veljkocerovic.timeformeal.user.appuser.AppUserService;
import com.veljkocerovic.timeformeal.user.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthValidations {

    @Autowired
    private AppUserService appUserService;

    public boolean checkIfUserIsOwner(Integer userId, Authentication authentication) throws UserNotFoundException {
        AppUser user = appUserService.findUserById(userId);
        return user.getUsername().equals(authentication.getName());
    }
}
