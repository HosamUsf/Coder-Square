package com.codersquare.response;

import java.time.LocalDateTime;

public record CommentDTO(
        long userId,
        long postId,
        long commentId,
        String text,
        int points,
        LocalDateTime createdAt


) {
}
