package com.veljkocerovic.timeformeal.api.registration;

import com.veljkocerovic.timeformeal.api.user.AppUser;
import com.veljkocerovic.timeformeal.api.user.AppUserService;
import com.veljkocerovic.timeformeal.api.registration.event.RegistrationCompleteEvent;
import com.veljkocerovic.timeformeal.api.user.models.PasswordModel;
import com.veljkocerovic.timeformeal.exceptions.*;
import com.veljkocerovic.timeformeal.response.ResponseMessage;
import com.veljkocerovic.timeformeal.utils.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseMessage> registerUser(@Valid @RequestBody AppUser appUser, final HttpServletRequest request)
            throws UserAlreadyExistsException {
        appUserService.saveUser(appUser);

        //Publish registration complete event
        publisher.publishEvent(new RegistrationCompleteEvent(
                appUser,
                Helpers.createAppUrl(request)
        ));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseMessage(
                        "User successfully created, please check your email for verification link."));

    }

    //VERIFY REGISTRATION
    @GetMapping("/verifyRegistration")
    public ResponseEntity<ResponseMessage> verifyRegistration(@RequestParam(name = "token") String token) throws
            TokenExpiredException,
            TokenNotFoundException {

        registrationService.validateVerificationToken(token);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("User verified successfully."));
    }

    //RESEND VERIFICATION TOKEN
    @GetMapping("/resendVerificationToken")
    public ResponseEntity<ResponseMessage> resendVerificationToken(@RequestParam(name = "token") String oldToken, HttpServletRequest request)
            throws TokenNotFoundException {

        registrationService.resendVerificationToken(oldToken, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("Verification link sent"));
    }


    //RESET PASSWORD
    @PostMapping("/resetPassword")
    public ResponseEntity<ResponseMessage> resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request)
            throws UserNotFoundException {
        registrationService.saveAndSendPasswordResetToken(passwordModel, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage(
                        "Reset password link has been sent to " + passwordModel.getEmail() + " mail."
                ));
    }

    //SAVE PASSWORD
    @PostMapping("/savePassword")
    public ResponseEntity<ResponseMessage> savePassword(@RequestParam("token") String token,
                               @RequestBody PasswordModel passwordModel) throws
            TokenExpiredException,
            TokenNotFoundException {

        //Validate token
        registrationService.validatePasswordResetToken(token);

        //Get appUser by token
        AppUser appUser = registrationService.getUserByPasswordResetToken(token);

        //Change password
        registrationService.updatePassword(appUser, passwordModel.getNewPassword());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("Password reset successfully"));
    }

    //CHANGE PASSWORD
    @PostMapping("/changePassword")
    public ResponseEntity<ResponseMessage> changePassword(@RequestBody PasswordModel passwordModel) throws
            UserNotFoundException, WrongPasswordException {
        registrationService.changePassword(passwordModel);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("Password Changed Successfully"));
    }
}
