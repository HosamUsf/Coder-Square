package com.codersquare.response;

public record RegistrationResponse(String status, String message) {
    public static RegistrationResponse success(String message) {
        return new RegistrationResponse("success", message);
    }

    public static RegistrationResponse error(String message) {
        return new RegistrationResponse("error", message);
    }


}
