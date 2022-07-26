package com.veljkocerovic.timeformeal.user.registration.event;

import com.veljkocerovic.timeformeal.user.appuser.AppUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private AppUser appUser;
    private String applicationUrl;

    public RegistrationCompleteEvent(AppUser appUser, String applicationUrl) {
        super(appUser);
        this.appUser = appUser;
        this.applicationUrl = applicationUrl;
    }

}
