package com.veljkocerovic.timeformeal.user.appuser;

import com.veljkocerovic.timeformeal.user.exceptions.UserAlreadyExistsException;
import com.veljkocerovic.timeformeal.user.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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


    //DELETE USER
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable(value = "id") Integer userId) throws UserNotFoundException {
        appUserService.deleteUser(userId);
        return "User successfully deleted.";
    }


    //UPDATE USER
    @PutMapping("/{id}")
    public String updateUser(@PathVariable(value = "id") Integer userId, @Valid @RequestBody AppUser appUser) throws
            UserNotFoundException, UserAlreadyExistsException {
        appUserService.updateUser(userId, appUser);
        return "User successfully updated.";
    }

}
