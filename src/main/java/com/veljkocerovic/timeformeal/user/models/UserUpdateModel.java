package com.veljkocerovic.timeformeal.user.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateModel {

    private String username;
    private String email;
    private MultipartFile image;
}
