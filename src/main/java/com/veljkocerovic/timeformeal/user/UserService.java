package com.veljkocerovic.timeformeal.user;

import com.veljkocerovic.timeformeal.user.exceptions.*;
import com.veljkocerovic.timeformeal.user.model.PasswordModel;
import com.veljkocerovic.timeformeal.user.model.User;

import javax.servlet.http.HttpServletRequest;
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

    User findUserByEmail(String email) throws UserNotFoundException;

    void validatePasswordResetToken(String token) throws TokenNotFoundException, TokenExpiredException;

    User getUserByPasswordResetToken(String token) throws TokenNotFoundException;

    void updatePassword(User user, String newPassword);

    void changePassword(PasswordModel passwordModel) throws UserNotFoundException, WrongPasswordException;

    void resendVerificationToken(String oldToken, HttpServletRequest request) throws TokenNotFoundException;

    void saveAndSendPasswordResetToken(PasswordModel passwordModel, HttpServletRequest request) throws UserNotFoundException;
}
