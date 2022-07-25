package com.veljkocerovic.timeformeal.user.event;

import com.veljkocerovic.timeformeal.services.EmailSenderService;
import com.veljkocerovic.timeformeal.user.UserService;
import com.veljkocerovic.timeformeal.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //Create verification token with link for user
        User user = event.getUser();
        String token = UUID.randomUUID().toString();

        //Save token in database
        userService.saveUserVerificationToken(token, user);

        //Send mail to user
        String url = event.getApplicationUrl() + "/verifyRegistration?token=" + token;
        String message = "Click the link to verify your account: " + url;

        emailSenderService.sendSimpleEmail(user.getEmail(), message, "Verify your account");

    }
}
