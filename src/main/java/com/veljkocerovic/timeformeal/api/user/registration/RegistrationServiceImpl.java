package com.veljkocerovic.timeformeal.api.user.registration;

import com.veljkocerovic.timeformeal.api.user.UserRepository;
import com.veljkocerovic.timeformeal.api.user.appuser.AppUser;
import com.veljkocerovic.timeformeal.api.user.appuser.AppUserService;
import com.veljkocerovic.timeformeal.api.user.models.PasswordModel;
import com.veljkocerovic.timeformeal.api.user.tokens.password.PasswordResetToken;
import com.veljkocerovic.timeformeal.api.user.tokens.password.PasswordResetTokenRepository;
import com.veljkocerovic.timeformeal.api.user.tokens.verification.VerificationToken;
import com.veljkocerovic.timeformeal.services.EmailSenderService;
import com.veljkocerovic.timeformeal.exceptions.TokenExpiredException;
import com.veljkocerovic.timeformeal.exceptions.TokenNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;
import com.veljkocerovic.timeformeal.exceptions.WrongPasswordException;
import com.veljkocerovic.timeformeal.api.user.tokens.verification.VerificationTokenRepository;
import com.veljkocerovic.timeformeal.utils.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    public void saveUserVerificationToken(String token, AppUser appUser) {
        VerificationToken verificationToken = new VerificationToken(appUser, token);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public void validateVerificationToken(String token) throws TokenNotFoundException,
            TokenExpiredException {
        Optional<VerificationToken> optionalToken = verificationTokenRepository.findByToken(token);

        //Check if token exists
        VerificationToken verificationToken = optionalToken
                .orElseThrow(() -> new TokenNotFoundException("Verification token doesn't exist"));

        //Check if token has expired
        AppUser appUser = verificationToken.getAppUser();
        Calendar calendar = Calendar.getInstance();

        if ((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            throw new TokenExpiredException("Verification token expired");
        }

        //Enable appUser
        appUser.setEnabled(true);
        userRepository.save(appUser);
    }

    @Override
    public void validatePasswordResetToken(String token) throws TokenNotFoundException, TokenExpiredException {
        Optional<PasswordResetToken> optionalToken = passwordResetTokenRepository.findByToken(token);

        //Check if token exists
        PasswordResetToken passwordResetToken = optionalToken
                .orElseThrow(() -> new TokenNotFoundException("Password reset token doesn't exist"));

        //Check if token has expired
        Calendar calendar = Calendar.getInstance();

        if ((passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            throw new TokenExpiredException("Verification token expired");
        }
    }

    @Override
    public AppUser getUserByPasswordResetToken(String token) throws TokenNotFoundException {
        return passwordResetTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Password reset token doesn't exist"))
                .getAppUser();
    }

    @Override
    public void changePassword(PasswordModel passwordModel) throws UserNotFoundException, WrongPasswordException {
        AppUser appUser = appUserService.findUserByEmail(passwordModel.getEmail());

        //Check if old password doesn't match
        if (!passwordEncoder.matches(passwordModel.getOldPassword(), appUser.getPassword())) {
            throw new WrongPasswordException("Wrong old password.");
        }

        appUser.setPassword(passwordEncoder.encode(passwordModel.getNewPassword()));
        userRepository.save(appUser);
    }

    @Override
    public void updatePassword(AppUser appUser, String newPassword) {
        appUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(appUser);
    }

    @Override
    public void resendVerificationToken(String oldToken, HttpServletRequest request) throws TokenNotFoundException {
        Optional<VerificationToken> optionalToken = verificationTokenRepository.findByToken(oldToken);

        //Check if token exists
        VerificationToken verificationToken = optionalToken
                .orElseThrow(() -> new TokenNotFoundException("Verification token doesn't exist"));

        //Generate new token
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);

        AppUser appUser = verificationToken.getAppUser();

        //Send mail with new token
        String url = Helpers.createAppUrl(request) + "/verifyRegistration?token=" + verificationToken.getToken();
        emailSenderService.sendSimpleEmail(
                appUser.getEmail(),
                "Click the link to verify your account: " + url,
                "Verify your account"
        );
    }

    @Override
    public void saveAndSendPasswordResetToken(PasswordModel passwordModel, HttpServletRequest request) throws
            UserNotFoundException {
        AppUser appUser = appUserService.findUserByEmail(passwordModel.getEmail());

        //Generate token
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken(appUser, token);

        //Save token to database
        passwordResetTokenRepository.save(passwordResetToken);


        //Send mail with reset token
        String url = Helpers.createAppUrl(request) + "/savePassword?token=" + passwordResetToken.getToken();
        emailSenderService.sendSimpleEmail(
                appUser.getEmail(),
                "Click the link to reset your password: " + url,
                "Reset your password"
        );
    }
}
