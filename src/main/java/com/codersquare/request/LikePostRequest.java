package com.codersquare.request;

import jakarta.validation.constraints.Positive;

public record LikePostRequest(
        @Positive long userId,
        @Positive long postId) {
}
