package com.veljkocerovic.timeformeal.user;

import com.veljkocerovic.timeformeal.api.user.UserRepository;
import com.veljkocerovic.timeformeal.api.user.AppUser;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

@DataJpaTest
public class AppUserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){

        AppUser testAppUser = new AppUser();
        testAppUser.setUsername("Veljko");
        testAppUser.setEmail("veljko@gmail.com");
        testAppUser.setPassword("veljko123");
        testAppUser.setImage("veljko_image.png");
        testAppUser.setEnabled(true);

        entityManager.persist(testAppUser);
        entityManager.flush();
    }


    @Test
    void testFindByUsername() throws UserNotFoundException {
        Optional<AppUser> foundUser = userRepository.findByUsername("Veljko");
        AppUser appUser = foundUser.orElseThrow(UserNotFoundException::new);

        Assertions.assertThat(appUser.getUsername()).isEqualTo("Veljko");
    }

    @Test
    void testFindByEmail() throws UserNotFoundException {
        Optional<AppUser> foundUser = userRepository.findByEmail("veljko@gmail.com");
        AppUser appUser = foundUser.orElseThrow(UserNotFoundException::new);

        Assertions.assertThat(appUser.getEmail()).isEqualTo("veljko@gmail.com");
    }

    @Test
    void testDeleteUserById() throws UserNotFoundException {
        Optional<AppUser> foundUser = userRepository.findByUsername("Veljko");
        AppUser appUser = foundUser.orElseThrow(UserNotFoundException::new);

        int changed = userRepository.deleteUserById(appUser.getId());

        Assertions.assertThat(changed).isGreaterThan(0);
    }
}
