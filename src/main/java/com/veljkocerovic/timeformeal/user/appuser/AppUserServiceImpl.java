package com.veljkocerovic.timeformeal.user.appuser;

import com.veljkocerovic.timeformeal.services.EmailSenderService;
import com.veljkocerovic.timeformeal.user.exceptions.*;
import com.veljkocerovic.timeformeal.user.model.PasswordModel;
import com.veljkocerovic.timeformeal.user.model.UserRole;
import com.veljkocerovic.timeformeal.user.tokens.password.PasswordResetToken;
import com.veljkocerovic.timeformeal.user.tokens.password.PasswordResetTokenRepository;
import com.veljkocerovic.timeformeal.user.tokens.verification.VerificationToken;
import com.veljkocerovic.timeformeal.user.tokens.verification.VerificationTokenRepository;
import com.veljkocerovic.timeformeal.utils.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class AppUserServiceImpl implements AppUserService {
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    public Set<AppUser> getAllUsers() {
        return appUserRepository.findAllByOrderByIdAsc();
    }

    @Override
    public void saveUser(AppUser appUser) throws UserAlreadyExistsException {
        //Check if username already exists
        Optional<AppUser> optionalUser = appUserRepository.findByUsername(appUser.getUsername());
        if (optionalUser.isPresent()) throw new UserAlreadyExistsException("AppUser " +
                optionalUser.get().getUsername() + " already exists.");


        //Check if email already exists
        Optional<AppUser> emailUser = appUserRepository.findByEmail(appUser.getEmail());
        if (emailUser.isPresent()) throw new UserAlreadyExistsException("AppUser with " +
                emailUser.get().getEmail() + " email already exists.");

        //Encrypt password
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        //Set default appUser role
        appUser.setRole(UserRole.USER);

        appUserRepository.save(appUser);
    }

    @Override
    public void deleteUser(Integer userId) {

    }

    @Override
    public AppUser findUserById(Integer userId) throws UserNotFoundException {
        Optional<AppUser> optionalUser = appUserRepository.findById(userId);

        return optionalUser.orElseThrow(() -> new UserNotFoundException("AppUser with " + userId + " doesn't exist."));
    }

    @Override
    public void updateUser(Integer userId, AppUser appUser) {

    }

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

        if((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            verificationTokenRepository.delete(verificationToken);
            throw new TokenExpiredException("Verification token expired");
        }

        //Enable appUser
        appUser.setEnabled(true);
        appUserRepository.save(appUser);
    }

    @Override
    public AppUser findUserByEmail(String email) throws UserNotFoundException {
        Optional<AppUser> optionalUser = appUserRepository.findByEmail(email);

        return optionalUser.orElseThrow(() -> new UserNotFoundException("AppUser with " + email + " email doesn't exist."));
    }


    @Override
    public void validatePasswordResetToken(String token) throws TokenNotFoundException, TokenExpiredException {
        Optional<PasswordResetToken> optionalToken = passwordResetTokenRepository.findByToken(token);

        //Check if token exists
        PasswordResetToken passwordResetToken = optionalToken
                .orElseThrow(() -> new TokenNotFoundException("Password reset token doesn't exist"));

        //Check if token has expired
        Calendar calendar = Calendar.getInstance();

        if((passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
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
        AppUser appUser = findUserByEmail(passwordModel.getEmail());

        //Check if old password doesn't match
        if(!passwordEncoder.matches(passwordModel.getOldPassword(), appUser.getPassword())){
            throw new WrongPasswordException("Wrong old password.");
        }

        appUser.setPassword(passwordEncoder.encode(passwordModel.getNewPassword()));
        appUserRepository.save(appUser);
    }

    @Override
    public void updatePassword(AppUser appUser, String newPassword) {
        appUser.setPassword(passwordEncoder.encode(newPassword));
        appUserRepository.save(appUser);
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
    public void saveAndSendPasswordResetToken(PasswordModel passwordModel, HttpServletRequest request) throws UserNotFoundException {
        AppUser appUser = findUserByEmail(passwordModel.getEmail());

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
