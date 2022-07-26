package com.veljkocerovic.timeformeal.user.tokens.password;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {

    Optional<PasswordResetToken> findByToken(String token);

    @Query("FROM PasswordResetToken as prt where prt.appUser.id = :userId")
    Optional<PasswordResetToken> findTokenByUserId(@Param("userId") Integer userId);
}
