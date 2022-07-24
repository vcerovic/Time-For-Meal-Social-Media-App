package com.veljkocerovic.timeformeal.user;

import com.veljkocerovic.timeformeal.user.model.User;
import com.veljkocerovic.timeformeal.user.model.UserRole;
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
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        User testUser = new User();
        testUser.setUsername("Veljko");
        testUser.setEmail("veljko@gmail.com");
        testUser.setPassword("veljko123");
        testUser.setImage("veljko_image.png");
        testUser.setEnabled(true);
        testUser.setUserRole(UserRole.ADMIN);

        entityManager.persist(testUser);
        entityManager.flush();
    }


    @Test
    void findByUsername() {
        Optional<User> foundUser = userRepository.findByUsername("Veljko");
        User user = foundUser.orElseThrow(RuntimeException::new);

        Assertions.assertThat(user.getUsername()).isEqualTo("Veljko");
    }

    @Test
    void findByEmail() {
        Optional<User> foundUser = userRepository.findByEmail("veljko@gmail.com");
        User user = foundUser.orElseThrow(RuntimeException::new);

        Assertions.assertThat(user.getEmail()).isEqualTo("veljko@gmail.com");
    }
}
