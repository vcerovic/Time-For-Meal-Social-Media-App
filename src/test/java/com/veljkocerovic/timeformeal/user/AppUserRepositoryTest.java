package com.veljkocerovic.timeformeal.user;

import com.veljkocerovic.timeformeal.user.appuser.AppUser;
import com.veljkocerovic.timeformeal.user.models.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@Rollback
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
        testAppUser.setRole(UserRole.ADMIN);

        entityManager.persist(testAppUser);
        entityManager.flush();
    }


    @Test
    void findByUsername() {
        Optional<AppUser> foundUser = userRepository.findByUsername("Veljko");
        AppUser appUser = foundUser.orElseThrow(RuntimeException::new);

        Assertions.assertThat(appUser.getUsername()).isEqualTo("Veljko");
    }

    @Test
    void findByEmail() {
        Optional<AppUser> foundUser = userRepository.findByEmail("veljko@gmail.com");
        AppUser appUser = foundUser.orElseThrow(RuntimeException::new);

        Assertions.assertThat(appUser.getEmail()).isEqualTo("veljko@gmail.com");
    }
}
