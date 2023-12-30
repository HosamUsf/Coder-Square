package com.codersquare.response;

import java.time.LocalDateTime;

public record UserDTO(
        long userId,
        String name,
        String username,
        String email,
        String postsUrl ,
        String number_of_post,
        String likesUrl ,
        LocalDateTime createdAt

) {

    public UserDTO {
        postsUrl=  "http://localhost:3001/api/v1/users/"+username+"/posts";
        likesUrl =   "http://localhost:3001/api/v1/users/"+username+"/likes";
    }
}
