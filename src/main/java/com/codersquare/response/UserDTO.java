package com.codersquare.response;

import java.time.LocalDateTime;

public record UserDTO(
        long userId,
        String name,
        String userName,
        String email,
        String posts_url ,
        String number_of_post,
        LocalDateTime createdAt

) {

    public UserDTO {
        posts_url=  "http://localhost:3001/api/v1/posts/" + userId;
    }
}
