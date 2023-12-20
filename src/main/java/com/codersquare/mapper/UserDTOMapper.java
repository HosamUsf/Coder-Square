package com.codersquare.mapper;


import com.codersquare.response.UserDto;
import com.codersquare.user.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDto> {


    @Override
    public UserDto apply(User user) {
        return new UserDto(
                user.getUserId(),
                user.getFirstName()+" "+user.getLastName(),
                user.getUserName(),
                user.getEmail(),
                "http://localhost:3001/api/v1/posts/"+user.getUserId(),
                user.getCreatedAt()
        );
    }
}
