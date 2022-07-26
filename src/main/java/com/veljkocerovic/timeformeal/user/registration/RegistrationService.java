package com.veljkocerovic.timeformeal.user.registration;

import com.veljkocerovic.timeformeal.user.appuser.AppUser;
import com.veljkocerovic.timeformeal.user.exceptions.TokenExpiredException;
import com.veljkocerovic.timeformeal.user.exceptions.TokenNotFoundException;
import com.veljkocerovic.timeformeal.user.exceptions.UserNotFoundException;
import com.veljkocerovic.timeformeal.user.exceptions.WrongPasswordException;
import com.veljkocerovic.timeformeal.user.model.PasswordModel;

import javax.servlet.http.HttpServletRequest;

public interface RegistrationService {
    void saveUserVerificationToken(String token, AppUser appUser);

    void validateVerificationToken(String token) throws TokenNotFoundException,
            TokenExpiredException;

    void validatePasswordResetToken(String token) throws TokenNotFoundException, TokenExpiredException;

    AppUser getUserByPasswordResetToken(String token) throws TokenNotFoundException;

    void updatePassword(AppUser appUser, String newPassword);

    void changePassword(PasswordModel passwordModel) throws UserNotFoundException, WrongPasswordException;

    void resendVerificationToken(String oldToken, HttpServletRequest request) throws TokenNotFoundException;

    void saveAndSendPasswordResetToken(PasswordModel passwordModel, HttpServletRequest request) throws UserNotFoundException;

}
