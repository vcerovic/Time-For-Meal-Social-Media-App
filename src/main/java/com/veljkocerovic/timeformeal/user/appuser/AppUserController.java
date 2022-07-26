package com.veljkocerovic.timeformeal.user.appuser;

import com.veljkocerovic.timeformeal.user.AuthUserService;
import com.veljkocerovic.timeformeal.user.event.RegistrationCompleteEvent;
import com.veljkocerovic.timeformeal.user.exceptions.*;
import com.veljkocerovic.timeformeal.user.model.JwtRequestModel;
import com.veljkocerovic.timeformeal.user.model.JwtResponseModel;
import com.veljkocerovic.timeformeal.user.model.PasswordModel;
import com.veljkocerovic.timeformeal.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Slf4j
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthUserService authUserService;


    @PostMapping
    public String registerUser(@Valid @RequestBody AppUser appUser, final HttpServletRequest request) throws UserAlreadyExistsException {
        appUserService.saveUser(appUser);
        publisher.publishEvent(new RegistrationCompleteEvent(
                appUser,
                request.getRequestURL().toString()
        ));
        return "Successfully registered new appUser";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam(name = "token") String token) throws TokenExpiredException,
            TokenNotFoundException {
        appUserService.validateVerificationToken(token);
        return "AppUser verified successfully";
    }

    @GetMapping("/resendVerificationToken")
    public String resendVerificationToken(@RequestParam(name = "token") String oldToken, HttpServletRequest request)
            throws TokenNotFoundException {

        appUserService.resendVerificationToken(oldToken, request);
        return "Verification link sent";
    }


    @GetMapping("/{id}")
    public AppUser getUserById(@PathVariable(value = "id") Integer userId) throws UserNotFoundException {
        return appUserService.findUserById(userId);
    }


    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request)
            throws UserNotFoundException {
        appUserService.saveAndSendPasswordResetToken(passwordModel, request);
        return "Reset password link has been sent to " + passwordModel.getEmail() + " mail.";
    }


    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token,
                                @RequestBody PasswordModel passwordModel) throws
            TokenExpiredException,
            TokenNotFoundException {

        //Validate token
        appUserService.validatePasswordResetToken(token);

        //Get appUser by token
        AppUser appUser = appUserService.getUserByPasswordResetToken(token);

        //Change password
        appUserService.updatePassword(appUser, passwordModel.getNewPassword());

        return "Password reset successfully";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel) throws
            UserNotFoundException, WrongPasswordException {
        appUserService.changePassword(passwordModel);
        return "Password Changed Successfully";
    }


    @PostMapping("/authenticate")
    public JwtResponseModel authenticateUser(@RequestBody JwtRequestModel jwtRequest) throws Exception {
        //Authenticate
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        UserDetails userDetails = authUserService.loadUserByUsername(jwtRequest.getUsername());

        //Generate token
        String token = jwtUtils.generateToken(userDetails);

        return new JwtResponseModel(token);
    }



}
