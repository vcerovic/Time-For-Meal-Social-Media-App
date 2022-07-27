package com.veljkocerovic.timeformeal.api.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Integer> {

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByEmail(String email);

}
