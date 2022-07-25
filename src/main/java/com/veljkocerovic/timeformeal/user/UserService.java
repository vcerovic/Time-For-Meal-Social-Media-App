package com.veljkocerovic.timeformeal.user;

import com.veljkocerovic.timeformeal.user.exceptions.UserAlreadyExistsException;
import com.veljkocerovic.timeformeal.user.exceptions.UserNotFoundException;
import com.veljkocerovic.timeformeal.user.exceptions.TokenExpiredException;
import com.veljkocerovic.timeformeal.user.exceptions.TokenNotFoundException;
import com.veljkocerovic.timeformeal.user.model.User;
import com.veljkocerovic.timeformeal.user.tokens.password.PasswordResetToken;
import com.veljkocerovic.timeformeal.user.tokens.verification.VerificationToken;

import java.util.Set;

public interface UserService {

    Set<User> getAllUsers();

    void saveUser(User user) throws UserAlreadyExistsException;

    void deleteUser(Integer userId);

    User findUserById(Integer userId) throws UserNotFoundException;

    void updateUser(Integer userId, User user);

    void saveUserVerificationToken(String token, User user);

    void validateVerificationToken(String token) throws TokenNotFoundException,
            TokenExpiredException;

    VerificationToken generateNewVerificationToken(String oldToken) throws TokenNotFoundException;

    User findUserByEmail(String email) throws UserNotFoundException;

    PasswordResetToken saveUserPasswordResetToken(User user);

    void validatePasswordResetToken(String token) throws TokenNotFoundException, TokenExpiredException;

    User getUserByPasswordResetToken(String token) throws TokenNotFoundException;

    void changePassword(User user, String newPassword);
}
