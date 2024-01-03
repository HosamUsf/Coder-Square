package com.codersquare.request;

import jakarta.validation.constraints.NotEmpty;

public record UpdateVoteRequest(
        @NotEmpty int userId,
        @NotEmpty int entityId,
        @NotEmpty String voteType
) {
}
