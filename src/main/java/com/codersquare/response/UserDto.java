package com.codersquare.response;

import java.time.LocalDateTime;

public record UserDto(
        long userId,
        String name,
        String userName,
        String email,
        String posts_url ,
        LocalDateTime createdAt

) {

    public UserDto  {
        posts_url=  "http://localhost:3001/api/v1/posts/" + userId;
    }
}
