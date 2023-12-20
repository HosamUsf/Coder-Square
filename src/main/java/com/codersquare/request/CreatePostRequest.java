package com.codersquare.request;

import jakarta.validation.constraints.NotEmpty;

public record CreatePostRequest(
         int userId,
        @NotEmpty String title,
        @NotEmpty String category,
        @NotEmpty String url
) {
}
