package com.veljkocerovic.timeformeal.user.event;

import com.veljkocerovic.timeformeal.services.EmailSenderService;
import com.veljkocerovic.timeformeal.user.appuser.AppUserService;
import com.veljkocerovic.timeformeal.user.appuser.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //Create verification token with link for appUser
        AppUser appUser = event.getAppUser();
        String token = UUID.randomUUID().toString();

        //Save token in database
        appUserService.saveUserVerificationToken(token, appUser);

        //Send mail to appUser
        String url = event.getApplicationUrl() + "/verifyRegistration?token=" + token;
        String message = "Click the link to verify your account: " + url;

        emailSenderService.sendSimpleEmail(appUser.getEmail(), message, "Verify your account");

    }
}
