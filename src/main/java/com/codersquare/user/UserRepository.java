package com.codersquare.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    void deleteUserByUsername(String username);

    void deleteAllByUsername(String username);
    Optional<User> findUserByUsernameOrEmail(String username,String email);



}
