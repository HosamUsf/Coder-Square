package com.codersquare.post;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {


    @Query("select p from Post p where p.user.userId = :userId")
    List<Post> findAllByUserId(Long userId);

    boolean existsByPostId(Long postId);

    @Transactional
    @Modifying
    @Query("UPDATE Post p set p.points = :points")
    void updatePostByPoints(int points);
}
