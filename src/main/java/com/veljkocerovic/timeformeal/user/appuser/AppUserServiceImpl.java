package com.veljkocerovic.timeformeal.user.appuser;

import com.veljkocerovic.timeformeal.services.EmailSenderService;
import com.veljkocerovic.timeformeal.user.UserRepository;
import com.veljkocerovic.timeformeal.user.exceptions.UserAlreadyExistsException;
import com.veljkocerovic.timeformeal.user.exceptions.UserNotFoundException;
import com.veljkocerovic.timeformeal.user.model.UserRole;
import com.veljkocerovic.timeformeal.user.tokens.password.PasswordResetTokenRepository;
import com.veljkocerovic.timeformeal.user.tokens.verification.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AppUserServiceImpl implements AppUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Set<AppUser> getAllUsers() {
        return userRepository.findAllByOrderByIdAsc();
    }

    @Override
    public void saveUser(AppUser appUser) throws UserAlreadyExistsException {
        //Check if username already exists
        Optional<AppUser> optionalUser = userRepository.findByUsername(appUser.getUsername());
        if (optionalUser.isPresent()) throw new UserAlreadyExistsException("AppUser " +
                optionalUser.get().getUsername() + " already exists.");


        //Check if email already exists
        Optional<AppUser> emailUser = userRepository.findByEmail(appUser.getEmail());
        if (emailUser.isPresent()) throw new UserAlreadyExistsException("AppUser with " +
                emailUser.get().getEmail() + " email already exists.");

        //Encrypt password
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        //Set default appUser role
        appUser.setRole(UserRole.USER);

        userRepository.save(appUser);
    }

    @Override
    public void deleteUser(Integer userId) {

    }

    @Override
    public AppUser findUserById(Integer userId) throws UserNotFoundException {
        Optional<AppUser> optionalUser = userRepository.findById(userId);

        return optionalUser.orElseThrow(() -> new UserNotFoundException("AppUser with " + userId + " doesn't exist."));
    }

    @Override
    public void updateUser(Integer userId, AppUser appUser) {

    }

    @Override
    public AppUser findUserByEmail(String email) throws UserNotFoundException {
        Optional<AppUser> optionalUser = userRepository.findByEmail(email);

        return optionalUser.orElseThrow(() -> new UserNotFoundException("AppUser with " + email + " email doesn't exist."));
    }
}
