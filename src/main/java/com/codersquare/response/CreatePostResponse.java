package com.codersquare.response;

public record CreatePostResponse(
        String status,
        String message,
        PostData data
) {
    public static CreatePostResponse success(String message , String postId){
        return new CreatePostResponse("success",message,new PostData(postId));
    }

    public static CreatePostResponse error(String message ){
        return new CreatePostResponse("error",message,null);
    }


    public record PostData(String postId ){}
}
