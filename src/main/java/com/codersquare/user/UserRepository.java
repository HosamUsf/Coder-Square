package com.codersquare.user;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    void deleteUserByUsername(String username);

    void deleteAllByUsername(String username);

    Optional<User> findUserByUsernameOrEmail(String username, String email);

    @Transactional
    @Modifying
    @Query("update User u  set u.enabled = TRUE where u.email = :email")
    void enableuser(String email);

}
