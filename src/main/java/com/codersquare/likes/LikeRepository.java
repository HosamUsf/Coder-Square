package com.codersquare.likes;

import com.codersquare.post.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("select l from Like l  where l.user.userId = :userId AND l.post.postId = :postId")
    Optional<Like> findLikeByUser_UserIdAndPost_PostId(long userId, long postId);

    int countAllByPost_PostId(long postId);

    List<Like> findLikeByUser_UserId(long userId);

    @Query("SELECT l.post FROM Like l WHERE l.user.userName = :userName")
    List<Post> findPostsByUserName(String userName);

    @Query("SELECT l FROM Like l WHERE l .post.postId = :postId")
    List<Like> findAllLikesBypostId(long postId);

    @Query("SELECT l FROM Like l WHERE l .user.userName = :userName")
    List<Like> findAllByUserName(String userName);


    @Transactional
    @Modifying
    void deleteAllByPost_PostId(Long postId);

    @Transactional
    @Modifying
    void deleteAllByUser_UserName(String userName);

    void deleteAllByPost(Post post);


}
