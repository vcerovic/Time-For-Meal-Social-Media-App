package com.veljkocerovic.timeformeal.user;

import com.veljkocerovic.timeformeal.user.event.RegistrationCompleteEvent;
import com.veljkocerovic.timeformeal.user.exceptions.UserAlreadyExistsException;
import com.veljkocerovic.timeformeal.user.exceptions.UserNotFoundException;
import com.veljkocerovic.timeformeal.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public Set<User> getAllUsers(){
        return userService.getAllUsers();
    }


    @PostMapping
    public String registerUser(@Valid @RequestBody User user) throws UserAlreadyExistsException {
        userService.saveUser(user);
        publisher.publishEvent(new RegistrationCompleteEvent(
                user,
                "url"
        ));
        return "Success";
    }


    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") Integer userId) throws UserNotFoundException {
        return userService.findUserById(userId);
    }


}
