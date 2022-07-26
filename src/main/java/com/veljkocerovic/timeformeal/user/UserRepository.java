package com.veljkocerovic.timeformeal.user;

import com.veljkocerovic.timeformeal.user.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Integer> {

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByEmail(String email);

}
