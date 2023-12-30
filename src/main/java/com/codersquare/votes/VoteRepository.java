package com.codersquare.votes;

import com.codersquare.post.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("select v from  Vote v where v.user.userId = :userId and v.post.postId = :postId")
    Optional<Vote> findVoteByUserIdAndPostId(long userId, long postId);

    @Query("select v from  Vote v where v.user.userId = :userId and v.comment.commentId = :commentId")
    Optional<Vote> findVoteByUserIdAndCommentId(long userId, long commentId);

    @Query("select v from Vote v where v.post.postId = :postId")
    List<Vote> findAllByPostId(Long postId);

    @Query("select v from Vote v where v.user.username = :userName")
    List<Vote> findAllByUsername(String userName);


    @Transactional
    @Modifying
    void deleteAllByPost_PostId(Long postId);

    @Transactional
    @Modifying
    void deleteAllByUser_Username(String userName);


    void deleteAllByPost(Post post);


}
