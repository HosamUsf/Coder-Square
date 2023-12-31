package com.codersquare.registration.token;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findTokenByConfirmationToken(String token);

    @Transactional
    @Modifying
    @Query("update Token  t  set t.confirmedAt = ?2 where t.confirmationToken = ?1")
    void updateTokenByConfirmedAt(String token, LocalDateTime now);
}
