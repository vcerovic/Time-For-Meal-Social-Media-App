package com.veljkocerovic.timeformeal.user.appuser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    Optional<AppUser> findByUsername(String username);

    Set<AppUser> findAllByOrderByIdAsc();

    Optional<AppUser> findByEmail(String email);

}
