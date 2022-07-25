package com.veljkocerovic.timeformeal.user;

import com.veljkocerovic.timeformeal.services.EmailSenderService;
import com.veljkocerovic.timeformeal.user.event.RegistrationCompleteEvent;
import com.veljkocerovic.timeformeal.user.exceptions.UserAlreadyExistsException;
import com.veljkocerovic.timeformeal.user.exceptions.UserNotFoundException;
import com.veljkocerovic.timeformeal.user.exceptions.VerificationTokenExpiredException;
import com.veljkocerovic.timeformeal.user.exceptions.VerificationTokenNotFoundException;
import com.veljkocerovic.timeformeal.user.model.User;
import com.veljkocerovic.timeformeal.user.token.VerificationToken;
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
    public String verifyRegistration(@RequestParam(name = "token") String token) throws VerificationTokenExpiredException,
            VerificationTokenNotFoundException {
        userService.validateVerificationToken(token);
        return "User verified successfully";
    }

    @GetMapping("/resendVerificationToken")
    public String resendVerificationToken(@RequestParam(name = "token") String oldToken, HttpServletRequest request)
            throws VerificationTokenNotFoundException {

        VerificationToken token = userService.generateNewVerificationToken(oldToken);

        User user = token.getUser();

        //Send mail with new token
        String url = applicationUrl(request) + "/users/verifyRegistration?token=" + token.getToken();
        String message = "Click the link to verify your account: " + url;

        emailSenderService.sendSimpleEmail(user.getEmail(), message, "Verify your account");


        return "Verification link sent";
    }


    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") Integer userId) throws UserNotFoundException {
        return userService.findUserById(userId);
    }



    private String applicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }

}
