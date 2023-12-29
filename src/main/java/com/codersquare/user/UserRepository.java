package com.codersquare.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUserName(String userName);

    boolean existsByEmail(String email);

    boolean existsByUserName(String username);

    void deleteUserByUserName(String userName);

    void deleteAllByUserName(String userName);



}
