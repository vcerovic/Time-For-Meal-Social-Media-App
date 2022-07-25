package com.veljkocerovic.timeformeal.user;

import com.veljkocerovic.timeformeal.services.EmailSenderService;
import com.veljkocerovic.timeformeal.user.event.RegistrationCompleteEvent;
import com.veljkocerovic.timeformeal.user.exceptions.UserAlreadyExistsException;
import com.veljkocerovic.timeformeal.user.exceptions.UserNotFoundException;
import com.veljkocerovic.timeformeal.user.exceptions.TokenExpiredException;
import com.veljkocerovic.timeformeal.user.exceptions.TokenNotFoundException;
import com.veljkocerovic.timeformeal.user.model.PasswordModel;
import com.veljkocerovic.timeformeal.user.model.User;
import com.veljkocerovic.timeformeal.user.tokens.password.PasswordResetToken;
import com.veljkocerovic.timeformeal.user.tokens.verification.VerificationToken;
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

    @Autowired
    private EmailSenderService emailSenderService;

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
        return "Success";
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

        VerificationToken token = userService.generateNewVerificationToken(oldToken);
        User user = token.getUser();

        //Send mail with new token
        String url = applicationUrl(request) + "/verifyRegistration?token=" + token.getToken();
        emailSenderService.sendSimpleEmail(
                user.getEmail(),
                "Click the link to verify your account: " + url,
                "Verify your account"
        );


        return "Verification link sent";
    }


    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") Integer userId) throws UserNotFoundException {
        return userService.findUserById(userId);
    }


    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request)
            throws UserNotFoundException {
        User user = userService.findUserByEmail(passwordModel.getEmail());
        PasswordResetToken token = userService.saveUserPasswordResetToken(user);

        //Send mail with reset token
        String url = applicationUrl(request) + "/savePassword?token=" + token.getToken();
        emailSenderService.sendSimpleEmail(
                user.getEmail(),
                "Click the link to reset your password: " + url,
                "Reset your password"
        );

        return "Reset password link has been sent to " + user.getEmail() + " mail.";
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
        userService.changePassword(user, passwordModel.getNewPassword());

        return "Password reset successfully";
    }


    private String applicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath() + "/users";
    }

}
