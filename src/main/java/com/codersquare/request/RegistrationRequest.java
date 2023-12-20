package com.codersquare.request;

import jakarta.validation.constraints.NotEmpty;

public record RegistrationRequest(
        @NotEmpty
        String firstName,
        @NotEmpty
        String lastName,
        @NotEmpty
        String userName,
        @NotEmpty
        String password,
        @NotEmpty
        String email
) {
}
