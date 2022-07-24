package com.veljkocerovic.timeformeal.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    }

    @Override
    public void deleteUser(Integer userId) {

    }

    @Override
    public User findUserById(Integer userId) {
        return null;
    }

    @Override
    public void updateUser(Integer userId, User user) {

    }
}
