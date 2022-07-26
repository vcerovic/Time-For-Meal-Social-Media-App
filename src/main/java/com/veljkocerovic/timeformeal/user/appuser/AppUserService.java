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

    void saveUserVerificationToken(String token, AppUser appUser);

    void validateVerificationToken(String token) throws TokenNotFoundException,
            TokenExpiredException;

    AppUser findUserByEmail(String email) throws UserNotFoundException;

    void validatePasswordResetToken(String token) throws TokenNotFoundException, TokenExpiredException;

    AppUser getUserByPasswordResetToken(String token) throws TokenNotFoundException;

    void updatePassword(AppUser appUser, String newPassword);

    void changePassword(PasswordModel passwordModel) throws UserNotFoundException, WrongPasswordException;

    void resendVerificationToken(String oldToken, HttpServletRequest request) throws TokenNotFoundException;

    void saveAndSendPasswordResetToken(PasswordModel passwordModel, HttpServletRequest request) throws UserNotFoundException;
}
