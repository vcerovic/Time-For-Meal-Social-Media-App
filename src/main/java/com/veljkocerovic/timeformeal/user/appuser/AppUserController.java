package com.veljkocerovic.timeformeal.user.appuser;

import com.veljkocerovic.timeformeal.user.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;


    //GET USER BY ID
    @GetMapping("/{id}")
    public AppUser getUserById(@PathVariable(value = "id") Integer userId) throws UserNotFoundException {
        return appUserService.findUserById(userId);
    }

}
