package com.veljkocerovic.timeformeal.user;


import com.veljkocerovic.timeformeal.api.user.AppUser;
import com.veljkocerovic.timeformeal.api.user.AppUserService;
import com.veljkocerovic.timeformeal.api.user.AppUserServiceImpl;
import com.veljkocerovic.timeformeal.api.user.UserRepository;
import com.veljkocerovic.timeformeal.exceptions.UserAlreadyExistsException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.BDDAssumptions.given;

@SpringBootTest
public class AppUserServiceTest {

    @Autowired
    private AppUserService appUserService;

    @MockBean
    private UserRepository userRepository;

    private AppUser testUser;


    @BeforeEach
    void setUp(){

        testUser = AppUser.builder()
                .id(1)
                .username("Veljko")
                .email("veljko@gmail.com")
                .password("veljko123")
                .image("veljko_image.png")
                .enabled(true)
                .build();

        Mockito.when(userRepository.findByEmail(testUser.getEmail()))
                .thenReturn(Optional.ofNullable(testUser));

        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.ofNullable(testUser));
    }


    @Test
    @DisplayName("If email already exist when saving user, throw exception")
    public void givenExistingEmail_whenSaveUser_thenThrowsException() {
        given(userRepository.findByEmail(testUser.getEmail()))
                .isEqualTo(Optional.of(testUser));

        UserAlreadyExistsException throwable =
                Assertions.catchThrowableOfType(() -> appUserService.saveUser(testUser), UserAlreadyExistsException.class);

        Assertions.assertThat(throwable.getMessage()).isNotNull();

    }

    @Test
    @DisplayName("If username already exist when saving user, throw exception")
    public void givenExistingUsername_whenSaveUser_thenThrowsException() {
        given(userRepository.findByUsername(testUser.getUsername()))
                .isEqualTo(Optional.of(testUser));

        UserAlreadyExistsException throwable =
                Assertions.catchThrowableOfType(() -> appUserService.saveUser(testUser), UserAlreadyExistsException.class);

        Assertions.assertThat(throwable.getMessage()).isNotNull();

    }
}
