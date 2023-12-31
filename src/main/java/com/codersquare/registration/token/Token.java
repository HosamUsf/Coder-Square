package com.codersquare.registration.token;

import com.codersquare.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    long tokenId;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    User user;

    @Column(name = "confirmation_token", nullable = false)
    String confirmationToken;

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @Column(name = "confirmed_at", nullable = false)
    LocalDateTime confirmedAt;

    @Column(name = "expired_at", nullable = false)
    LocalDateTime expiredAt;

    public Token(User user, String confirmationToken) {
        this.user = user;
        this.confirmationToken = confirmationToken;
        this.createdAt = LocalDateTime.now();
        this.expiredAt = LocalDateTime.now().plusMinutes(15);
    }
}
