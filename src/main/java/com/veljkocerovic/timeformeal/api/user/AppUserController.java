package com.veljkocerovic.timeformeal.api.user;

import com.veljkocerovic.timeformeal.api.user.models.UserUpdateModel;
import com.veljkocerovic.timeformeal.exceptions.ImageSizeLimitException;
import com.veljkocerovic.timeformeal.exceptions.UserAlreadyExistsException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("@securityService.isOwner(#userId)")
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable(value = "id") Integer userId) throws
            UserNotFoundException {
        appUserService.deleteUser(userId);
        return "User successfully deleted.";
    }


    //UPDATE USER
    @PreAuthorize("@securityService.isOwner(#userId)")
    @PutMapping("/{id}")
    public String updateUser(@PathVariable(value = "id") Integer userId,
                             @Valid @ModelAttribute UserUpdateModel user) throws
            UserNotFoundException, UserAlreadyExistsException, ImageSizeLimitException {
        appUserService.updateUser(userId, user);
        return "User successfully updated.";
    }

    //GET USER IMAGE
    @GetMapping(
            value = "/{id}/image",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
    )
    public @ResponseBody byte[] getUserImage(@PathVariable(value = "id") Integer userId) throws UserNotFoundException {
            return appUserService.getUserImage(userId);
    }

}
