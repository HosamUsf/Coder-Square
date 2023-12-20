package com.codersquare.response;

public record RegistrationResponse(String status, String message, UserData data) {
    public static RegistrationResponse success(String message, String userId) {
        return new RegistrationResponse("success", message, new UserData(userId));
    }

    public static RegistrationResponse error(String message) {
        return new RegistrationResponse("error", message, null);
    }

    public record UserData(String userId) {
    }
}
