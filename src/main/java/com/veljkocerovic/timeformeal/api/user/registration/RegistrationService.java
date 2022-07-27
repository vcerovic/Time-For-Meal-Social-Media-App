package com.veljkocerovic.timeformeal.api.user.registration;

import com.veljkocerovic.timeformeal.api.user.AppUser;
import com.veljkocerovic.timeformeal.exceptions.TokenExpiredException;
import com.veljkocerovic.timeformeal.exceptions.TokenNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.WrongPasswordException;
import com.veljkocerovic.timeformeal.api.user.models.PasswordModel;

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
