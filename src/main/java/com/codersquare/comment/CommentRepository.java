package com.codersquare.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query("select c from Comment c where c.post.postId = :postId")
    List<Comment> findAllByPostPostId (Long postId);

    boolean existsByCommentId(Long commentId);
}
