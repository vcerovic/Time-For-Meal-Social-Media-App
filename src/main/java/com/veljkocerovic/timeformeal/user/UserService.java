package com.veljkocerovic.timeformeal.user;

import com.veljkocerovic.timeformeal.user.model.User;

import java.util.Set;

public interface UserService {

    Set<User> getAllUsers();

    void saveUser(User user);

    void deleteUser(Integer userId);

    User findUserById(Integer userId);

    void updateUser(Integer userId, User user);
}
