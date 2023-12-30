package com.codersquare.post;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {


    @Query("select p from Post p where p.user.username = :username")
    List<Post> findAllByUserId(String username);

    boolean existsByPostId(Long postId);

    @Transactional
    @Modifying
    @Query("UPDATE Post p set p.points = :points")
    void updatePostByPoints(int points);

    @Query("SELECT p FROM Post p ORDER BY p.createdAt desc ")
    Page<Post> findByOrderByCreatedAtDesc(Pageable pagable);


    @Query("SELECT p FROM Post p  ORDER BY p.points DESC, p.createdAt DESC")
    Page<Post> findByOrderByPointsDesc(Pageable pagaple);


    @Query("select p from Post p  where p.points > 1")
    Page<Post> findHotPosts(Pageable pagaple);

    List<Post> findByUser_Username(String username);


}
