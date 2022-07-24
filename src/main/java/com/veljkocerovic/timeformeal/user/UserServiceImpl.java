package com.veljkocerovic.timeformeal.user;

import com.veljkocerovic.timeformeal.user.exceptions.UserNotFoundException;
import com.veljkocerovic.timeformeal.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public Set<User> getAllUsers() {
        return userRepository.findAllByOrderByIdAsc();
    }

    @Override
    public void saveUser(User user) {
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
}
