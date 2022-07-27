package com.veljkocerovic.timeformeal.api.user.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateModel {

    @NotEmpty
    @Size(min = 4, max = 50, message = "Name must be between 4 and 50 characters")
    private String username;

    @Email
    private String email;

    private MultipartFile image;
}
