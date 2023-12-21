package com.codersquare.response;

public record UpdateVoteResponse(
        String status,
        String message,
        VoteData data,
        ErrorData error
) {


    public record VoteData(
            String userId,
            String postId,
            String voteId
    ) {

    }

    public record ErrorData(
            String code,
            String details
    ) {
    }

    public static UpdateVoteResponse success(String message,VoteData data){
        return new  UpdateVoteResponse("success", message,data,null);
    }

    public static UpdateVoteResponse error(String message,ErrorData data){
        return new  UpdateVoteResponse("error", message,null,data);
    }
}
