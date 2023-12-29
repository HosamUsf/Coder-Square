package com.codersquare.mapper;


import com.codersquare.response.UserDTO;
import com.codersquare.user.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {


    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getFirstName() + " " + user.getLastName(),
                user.getUserName(),
                user.getEmail(),
                "http://localhost:3001/api/v1/users/" + user.getUserName() + "/posts",
                String.valueOf(user.getPosts().size()),
                 "http://localhost:3001/api/v1/users/" + user.getUserName() +"/likes",
                user.getCreatedAt()
        );
    }
}
