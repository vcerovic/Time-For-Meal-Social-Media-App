package com.veljkocerovic.timeformeal.api.user;

import com.veljkocerovic.timeformeal.api.user.models.UserUpdateModel;
import com.veljkocerovic.timeformeal.exceptions.ImageSizeLimitException;
import com.veljkocerovic.timeformeal.exceptions.UserAlreadyExistsException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;
import com.veljkocerovic.timeformeal.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public AppUser getUserByUsername(@RequestParam(value = "username") String username) throws UserNotFoundException {
        return appUserService.findUserByUsername(username);
    }

    //DELETE USER
    @PreAuthorize("@securityService.isOwner(#userId)")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteUser(@PathVariable(value = "id") Integer userId) throws
            UserNotFoundException {
        appUserService.deleteUser(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("User successfully deleted."));

    }


    //UPDATE USER
    @PreAuthorize("@securityService.isOwner(#userId)")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateUser(@PathVariable(value = "id") Integer userId,
                             @Valid @ModelAttribute UserUpdateModel user) throws
            UserNotFoundException, UserAlreadyExistsException, ImageSizeLimitException {
        appUserService.updateUser(userId, user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("User successfully updated."));
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
