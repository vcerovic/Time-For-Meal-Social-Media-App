package com.veljkocerovic.timeformeal.user;

import com.veljkocerovic.timeformeal.user.event.RegistrationCompleteEvent;
import com.veljkocerovic.timeformeal.user.exceptions.*;
import com.veljkocerovic.timeformeal.user.model.PasswordModel;
import com.veljkocerovic.timeformeal.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public Set<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @PostMapping
    public String registerUser(@Valid @RequestBody User user, final HttpServletRequest request) throws UserAlreadyExistsException {
        userService.saveUser(user);
        publisher.publishEvent(new RegistrationCompleteEvent(
                user,
                request.getRequestURL().toString()
        ));
        return "Successfully registered new user";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam(name = "token") String token) throws TokenExpiredException,
            TokenNotFoundException {
        userService.validateVerificationToken(token);
        return "User verified successfully";
    }

    @GetMapping("/resendVerificationToken")
    public String resendVerificationToken(@RequestParam(name = "token") String oldToken, HttpServletRequest request)
            throws TokenNotFoundException {

        userService.resendVerificationToken(oldToken, request);
        return "Verification link sent";
    }


    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") Integer userId) throws UserNotFoundException {
        return userService.findUserById(userId);
    }


    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request)
            throws UserNotFoundException {
        userService.saveAndSendPasswordResetToken(passwordModel, request);
        return "Reset password link has been sent to " + passwordModel.getEmail() + " mail.";
    }


    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token,
                                @RequestBody PasswordModel passwordModel) throws
            TokenExpiredException,
            TokenNotFoundException {

        //Validate token
        userService.validatePasswordResetToken(token);

        //Get user by token
        User user = userService.getUserByPasswordResetToken(token);

        //Change password
        userService.updatePassword(user, passwordModel.getNewPassword());

        return "Password reset successfully";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel) throws
            UserNotFoundException, WrongPasswordException {
        userService.changePassword(passwordModel);
        return "Password Changed Successfully";
    }




}
