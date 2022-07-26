package com.veljkocerovic.timeformeal.user.appuser;

import com.veljkocerovic.timeformeal.user.exceptions.*;
import com.veljkocerovic.timeformeal.user.model.PasswordModel;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public interface AppUserService {

    Set<AppUser> getAllUsers();

    void saveUser(AppUser appUser) throws UserAlreadyExistsException;

    void deleteUser(Integer userId);

    AppUser findUserById(Integer userId) throws UserNotFoundException;

    void updateUser(Integer userId, AppUser appUser);

    AppUser findUserByEmail(String email) throws UserNotFoundException;


}
