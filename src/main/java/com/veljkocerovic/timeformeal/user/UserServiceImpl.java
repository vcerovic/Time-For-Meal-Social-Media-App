package com.veljkocerovic.timeformeal.user;

import com.veljkocerovic.timeformeal.user.exceptions.UserAlreadyExistsException;
import com.veljkocerovic.timeformeal.user.exceptions.UserNotFoundException;
import com.veljkocerovic.timeformeal.user.model.User;
import com.veljkocerovic.timeformeal.user.model.UserRole;
import com.veljkocerovic.timeformeal.user.token.VerificationToken;
import com.veljkocerovic.timeformeal.user.token.VerificationTokenRepository;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        System.out.println("CALLED");
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }
}
