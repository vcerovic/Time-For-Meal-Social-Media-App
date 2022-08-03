package com.veljkocerovic.timeformeal.api.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Integer> {

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("delete from AppUser u where u.id = ?1")
    int deleteUserById(Integer userId);

}
