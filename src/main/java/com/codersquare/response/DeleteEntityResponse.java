package com.codersquare.response;

public record DeleteEntityResponse(
        String status,
        String message,
        String details
) {
    public static DeleteEntityResponse success(String message, String type, String postId) {
        return new DeleteEntityResponse("success",
                message + " deleted successfully",
                message + " with " + type + " " + postId + " Successfully deleted");
    }

    public static DeleteEntityResponse error(String message, String type, String postId) {
        return new DeleteEntityResponse("error",
                "Failed to delete " + message,
                message + " with " + type + " " + postId + " not found");
    }
}
