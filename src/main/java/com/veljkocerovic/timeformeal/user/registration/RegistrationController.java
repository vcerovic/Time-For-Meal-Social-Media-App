package com.veljkocerovic.timeformeal.user.registration;

import com.veljkocerovic.timeformeal.user.appuser.AppUser;
import com.veljkocerovic.timeformeal.user.appuser.AppUserService;
import com.veljkocerovic.timeformeal.user.registration.event.RegistrationCompleteEvent;
import com.veljkocerovic.timeformeal.user.exceptions.*;
import com.veljkocerovic.timeformeal.user.model.PasswordModel;
import com.veljkocerovic.timeformeal.utils.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private ApplicationEventPublisher publisher;


    //REGISTER USER
    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody AppUser appUser, final HttpServletRequest request)
            throws UserAlreadyExistsException {
        appUserService.saveUser(appUser);

        //Publish registration complete event
        publisher.publishEvent(new RegistrationCompleteEvent(
                appUser,
                Helpers.createAppUrl(request)
        ));

        return "Successfully registered new user";
    }

    //VERIFY REGISTRATION
    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam(name = "token") String token) throws
            TokenExpiredException,
            TokenNotFoundException {

        registrationService.validateVerificationToken(token);
        return "User verified successfully";
    }

    //RESEND VERIFICATION TOKEN
    @GetMapping("/resendVerificationToken")
    public String resendVerificationToken(@RequestParam(name = "token") String oldToken, HttpServletRequest request)
            throws TokenNotFoundException {

        registrationService.resendVerificationToken(oldToken, request);
        return "Verification link sent";
    }


    //RESET PASSWORD
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request)
            throws UserNotFoundException {
        registrationService.saveAndSendPasswordResetToken(passwordModel, request);
        return "Reset password link has been sent to " + passwordModel.getEmail() + " mail.";
    }

    //SAVE PASSWORD
    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token,
                               @RequestBody PasswordModel passwordModel) throws
            TokenExpiredException,
            TokenNotFoundException {

        //Validate token
        registrationService.validatePasswordResetToken(token);

        //Get appUser by token
        AppUser appUser = registrationService.getUserByPasswordResetToken(token);

        //Change password
        registrationService.updatePassword(appUser, passwordModel.getNewPassword());

        return "Password reset successfully";
    }

    //CHANGE PASSWORD
    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel) throws
            UserNotFoundException, WrongPasswordException {
        registrationService.changePassword(passwordModel);
        return "Password Changed Successfully";
    }
}
