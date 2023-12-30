package com.codersquare.response;

import java.time.LocalDateTime;
import java.util.List;

public record PostsDTO(
        long postId,
        String title,
        String category,
        String url,
        int points,
        LocalDateTime createdAt,
        int numberOfComments,
        UserDTO owner
) {
}
