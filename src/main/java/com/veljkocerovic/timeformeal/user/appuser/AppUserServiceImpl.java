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
    public void saveUser(AppUser appUser) throws UserAlreadyExistsException {
        checkIfUsernameAlreadyExists(appUser.getUsername());
        checkIfEmailAlreadyExists(appUser.getEmail());

        //Encrypt password
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        //Set default appUser role
        appUser.setRole(UserRole.USER);

        userRepository.save(appUser);
    }

    private void checkIfEmailAlreadyExists(String email) throws UserAlreadyExistsException {
        //Check if username already exists
        Optional<AppUser> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) throw new UserAlreadyExistsException("User with " +
                optionalUser.get().getEmail() + " email already exists.");
    }

    private void checkIfUsernameAlreadyExists(String username) throws UserAlreadyExistsException {
        //Check if username already exists
        Optional<AppUser> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) throw new UserAlreadyExistsException("User " +
                optionalUser.get().getUsername() + " already exists.");
    }

    @Override
    public void deleteUser(Integer userId) throws UserNotFoundException {
        AppUser user = findUserById(userId);
        userRepository.delete(user);
    }

    @Override
    public AppUser findUserById(Integer userId) throws UserNotFoundException {
        Optional<AppUser> optionalUser = userRepository.findById(userId);

        return optionalUser.orElseThrow(() -> new UserNotFoundException("AppUser with " + userId + " doesn't exist."));
    }

    @Override
    public void updateUser(Integer userId, AppUser newUser) throws UserNotFoundException, UserAlreadyExistsException {
        AppUser oldUser = findUserById(userId);

        //Check if username is taken
        if(!oldUser.getUsername().equals(newUser.getUsername())){
            checkIfUsernameAlreadyExists(newUser.getUsername());
            oldUser.setUsername(newUser.getUsername());
        }

        //Check if email is taken
        if(!oldUser.getEmail().equals(newUser.getEmail())){
            checkIfEmailAlreadyExists(newUser.getEmail());
            oldUser.setEmail(newUser.getEmail());
        }

        //Save user
        userRepository.save(oldUser);
    }

    @Override
    public AppUser findUserByEmail(String email) throws UserNotFoundException {
        Optional<AppUser> optionalUser = userRepository.findByEmail(email);

        return optionalUser.orElseThrow(() -> new UserNotFoundException("AppUser with " + email + " email doesn't exist."));
    }
}
