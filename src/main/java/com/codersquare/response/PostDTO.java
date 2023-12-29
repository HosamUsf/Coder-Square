package com.codersquare.response;

import java.time.LocalDateTime;
import java.util.List;

public record PostDTO(
        long postId,
        String title,
        String category,
        String url,
        int points,
        LocalDateTime createdAt,
        String Comments_number,
        UserDTO owner,
        List<CommentDTO> comments
) {
}
