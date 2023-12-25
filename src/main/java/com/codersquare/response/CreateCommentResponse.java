package com.codersquare.response;

public record CreateCommentResponse(
        String status,
        String message,
        CommentData data
) {

    public static CreateCommentResponse success(String message, String userId, String postId, String commentId) {
        return new CreateCommentResponse("success", message, new CommentData(userId, postId, commentId));
    }

    public static CreateCommentResponse error(String message) {
        return new CreateCommentResponse("error", message, null);
    }

    public record CommentData(
            String userId,
            String postId,
            String commentId
    ) {
    }
}
