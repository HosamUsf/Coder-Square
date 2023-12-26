package com.codersquare.votes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.w3c.dom.ls.LSInput;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("select v from  Vote v where v.user.userId = :userId and v.post.postId = :postId")
    Optional<Vote> findVoteByUserIdAndPostId(long userId, long postId);

    @Query("select v from  Vote v where v.user.userId = :userId and v.comment.commentId = :commentId")
    Optional<Vote> findVoteByUserIdAndCommentId(long userId, long commentId);

    @Query("select v from Vote v where v.post.postId = :postId")
    List<Vote> findAllByPostId(Long postId);
}
