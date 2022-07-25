package com.veljkocerovic.timeformeal.user;

import com.veljkocerovic.timeformeal.services.EmailSenderService;
import com.veljkocerovic.timeformeal.user.exceptions.*;
import com.veljkocerovic.timeformeal.user.model.PasswordModel;
import com.veljkocerovic.timeformeal.user.model.User;
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
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    public Set<User> getAllUsers() {
        return userRepository.findAllByOrderByIdAsc();
    }

    @Override
    public void saveUser(User user) throws UserAlreadyExistsException {
        //Check if username already exists
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isPresent()) throw new UserAlreadyExistsException("User " +
                optionalUser.get().getUsername() + " already exists.");


        //Check if email already exists
        Optional<User> emailUser = userRepository.findByEmail(user.getEmail());
        if (emailUser.isPresent()) throw new UserAlreadyExistsException("User with " +
                emailUser.get().getEmail() + " email already exists.");

        //Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //Set default user role
        user.setRole(UserRole.USER);

        userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer userId) {

    }

    @Override
    public User findUserById(Integer userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);

        return optionalUser.orElseThrow(() -> new UserNotFoundException("User with " + userId + " doesn't exist."));
    }

    @Override
    public void updateUser(Integer userId, User user) {

    }

    @Override
    public void saveUserVerificationToken(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);
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
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            verificationTokenRepository.delete(verificationToken);
            throw new TokenExpiredException("Verification token expired");
        }

        //Enable user
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        return optionalUser.orElseThrow(() -> new UserNotFoundException("User with " + email + " email doesn't exist."));
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
    public User getUserByPasswordResetToken(String token) throws TokenNotFoundException {
        return passwordResetTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Password reset token doesn't exist"))
                .getUser();
    }

    @Override
    public void changePassword(PasswordModel passwordModel) throws UserNotFoundException, WrongPasswordException {
        User user = findUserByEmail(passwordModel.getEmail());

        //Check if old password doesn't match
        if(!passwordEncoder.matches(passwordModel.getOldPassword(), user.getPassword())){
            throw new WrongPasswordException("Wrong old password.");
        }

        user.setPassword(passwordEncoder.encode(passwordModel.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
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

        User user = verificationToken.getUser();

        //Send mail with new token
        String url = Helpers.createAppUrl(request) + "/verifyRegistration?token=" + verificationToken.getToken();
        emailSenderService.sendSimpleEmail(
                user.getEmail(),
                "Click the link to verify your account: " + url,
                "Verify your account"
        );
    }

    @Override
    public void saveAndSendPasswordResetToken(PasswordModel passwordModel, HttpServletRequest request) throws UserNotFoundException {
        User user = findUserByEmail(passwordModel.getEmail());

        //Generate token
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);

        //Save token to database
        passwordResetTokenRepository.save(passwordResetToken);


        //Send mail with reset token
        String url = Helpers.createAppUrl(request) + "/savePassword?token=" + passwordResetToken.getToken();
        emailSenderService.sendSimpleEmail(
                user.getEmail(),
                "Click the link to reset your password: " + url,
                "Reset your password"
        );
    }
}
