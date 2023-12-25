package com.codersquare.request;

import jakarta.validation.constraints.NotEmpty;

public record CreateCommentRequest(
        int userId,
        int postId,
        @NotEmpty String text
) {
}
