package com.veljkocerovic.timeformeal.user.tokens.verification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {

    Optional<VerificationToken> findByToken(String token);

    @Query("FROM VerificationToken as vt where vt.appUser.id = :userId")
    Optional<VerificationToken> findTokenByUserId(@Param("userId") Integer userId);
}
