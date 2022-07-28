package com.veljkocerovic.timeformeal.api.user;

import com.veljkocerovic.timeformeal.api.user.models.UserUpdateModel;
import com.veljkocerovic.timeformeal.exceptions.ImageSizeLimitException;
import com.veljkocerovic.timeformeal.exceptions.UserAlreadyExistsException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;
import org.springframework.web.multipart.MultipartFile;

public interface AppUserService {

    void saveUser(AppUser appUser) throws UserAlreadyExistsException;

    void deleteUser(Integer userId) throws UserNotFoundException;

    AppUser findUserById(Integer userId) throws UserNotFoundException;

    void updateUser(Integer userId, UserUpdateModel newUser) throws UserNotFoundException, UserAlreadyExistsException, ImageSizeLimitException;

    AppUser findUserByEmail(String email) throws UserNotFoundException;

    AppUser findUserByUsername(String username) throws UserNotFoundException;

    byte[] getUserImage(Integer userId) throws UserNotFoundException;

}
