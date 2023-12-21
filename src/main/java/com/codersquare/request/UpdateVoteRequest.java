package com.codersquare.request;

import jakarta.validation.constraints.NotEmpty;

public record UpdateVoteRequest(
        @NotEmpty int userId,
        @NotEmpty int postId,
        @NotEmpty String voteType
) {
}
