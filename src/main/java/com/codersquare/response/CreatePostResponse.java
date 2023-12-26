package com.codersquare.response;

public record CreatePostResponse(
        String status,
        String message,
        PostData data,
        ErrorData error
) {

    public static CreatePostResponse success(String message, String postId) {
        return new CreatePostResponse("success", message, new PostData(postId),null);
    }

    public static CreatePostResponse error(String message,ErrorData data) {
        return new CreatePostResponse("error", message, null,data);
    }


    public record PostData(String postId) {
    }

    public record ErrorData(
            String code,
            String details
    ) {
    }
}
