package com.veljkocerovic.timeformeal.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Set<User> findAllByOrderByIdAsc();

    Optional<User> findByEmail(String email);
}
