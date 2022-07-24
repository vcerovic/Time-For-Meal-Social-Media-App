package com.veljkocerovic.timeformeal.user;

import com.veljkocerovic.timeformeal.user.exceptions.UserAlreadyExistsException;
import com.veljkocerovic.timeformeal.user.exceptions.UserNotFoundException;
import com.veljkocerovic.timeformeal.user.model.User;

import java.util.Set;

public interface UserService {

    Set<User> getAllUsers();

    void saveUser(User user) throws UserAlreadyExistsException;

    void deleteUser(Integer userId);

    User findUserById(Integer userId) throws UserNotFoundException;

    void updateUser(Integer userId, User user);
}
