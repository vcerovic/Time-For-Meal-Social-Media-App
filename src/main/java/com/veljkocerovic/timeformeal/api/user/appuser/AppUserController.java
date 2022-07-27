package com.veljkocerovic.timeformeal.api.user.appuser;

import com.veljkocerovic.timeformeal.exceptions.ImageSizeLimitException;
import com.veljkocerovic.timeformeal.exceptions.UserAlreadyExistsException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;
import com.veljkocerovic.timeformeal.api.user.models.UserUpdateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    @PreAuthorize("@authValidations.checkIfUserIsOwner(#userId, #authentication)")
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable(value = "id") Integer userId, Authentication authentication) throws
            UserNotFoundException {
        appUserService.deleteUser(userId);
        return "User successfully deleted.";
    }


    //UPDATE USER
    @PreAuthorize("@authValidations.checkIfUserIsOwner(#userId, #authentication)")
    @PutMapping("/{id}")
    public String updateUser(@PathVariable(value = "id") Integer userId,
                             @Valid @ModelAttribute UserUpdateModel user,
                             Authentication authentication) throws
            UserNotFoundException, UserAlreadyExistsException, ImageSizeLimitException {
        appUserService.updateUser(userId, user);
        return "User successfully updated.";
    }

}
