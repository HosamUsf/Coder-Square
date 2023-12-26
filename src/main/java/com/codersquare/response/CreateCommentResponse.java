package com.codersquare.response;

public record CreateCommentResponse(
        String status,
        String message,
        CommentData data,
        ErrorData error
) {
    public record CommentData(
            String userId,
            String postId,
            String commentId
    ) {
    }

    public record ErrorData(
            String code,
            String details
    ) {
    }

    public static CreateCommentResponse success(String message, String userId, String postId, String commentId) {
        return new CreateCommentResponse("success", message, new CommentData(userId, postId, commentId), null);
    }

    public static CreateCommentResponse error(String message,ErrorData data) {
        return new CreateCommentResponse("error", message, null, data);
    }

}
