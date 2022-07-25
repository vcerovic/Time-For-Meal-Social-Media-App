package com.veljkocerovic.timeformeal.user;

import com.veljkocerovic.timeformeal.user.exceptions.UserAlreadyExistsException;
import com.veljkocerovic.timeformeal.user.exceptions.UserNotFoundException;
import com.veljkocerovic.timeformeal.user.exceptions.VerificationTokenExpiredException;
import com.veljkocerovic.timeformeal.user.exceptions.VerificationTokenNotFoundException;
import com.veljkocerovic.timeformeal.user.model.User;
import com.veljkocerovic.timeformeal.user.token.VerificationToken;

import java.util.Set;

public interface UserService {

    Set<User> getAllUsers();

    void saveUser(User user) throws UserAlreadyExistsException;

    void deleteUser(Integer userId);

    User findUserById(Integer userId) throws UserNotFoundException;

    void updateUser(Integer userId, User user);

    void saveUserVerificationToken(String token, User user);

    void validateVerificationToken(String token) throws VerificationTokenNotFoundException,
            VerificationTokenExpiredException;

    VerificationToken generateNewVerificationToken(String oldToken) throws VerificationTokenNotFoundException;
}
