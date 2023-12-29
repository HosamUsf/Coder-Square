package com.codersquare.response;

public record LikePostResponse(
        String status,
        String message,
        String action,
        LikeData likedBy,
        Integer likesCount,
        ErrorData error
) {


    public record ErrorData(
            String code,
            String details
    ) {
    }

    public record LikeData(
            long userId,
            long PostId
    ) {
    }

    public static LikePostResponse success(String message, String action, LikeData data, int likesCount) {
        return new LikePostResponse("success", message, action, data, likesCount, null);
    }

    public static LikePostResponse error(String message, ErrorData data) {
        return new LikePostResponse("error", message, null,null,null, data);
    }
}
