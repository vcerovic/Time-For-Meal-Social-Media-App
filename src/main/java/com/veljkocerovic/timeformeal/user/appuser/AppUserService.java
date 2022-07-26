package com.veljkocerovic.timeformeal.user.appuser;

import com.veljkocerovic.timeformeal.user.exceptions.*;
import com.veljkocerovic.timeformeal.user.models.UserUpdateModel;

public interface AppUserService {

    void saveUser(AppUser appUser) throws UserAlreadyExistsException;

    void deleteUser(Integer userId) throws UserNotFoundException;

    AppUser findUserById(Integer userId) throws UserNotFoundException;

    void updateUser(Integer userId, UserUpdateModel newUser) throws UserNotFoundException, UserAlreadyExistsException, ImageSizeLimitException;

    AppUser findUserByEmail(String email) throws UserNotFoundException;

    AppUser findUserByUsername(String username) throws UserNotFoundException;

}
