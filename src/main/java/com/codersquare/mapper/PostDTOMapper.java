package com.codersquare.mapper;

import com.codersquare.post.Post;
import com.codersquare.response.PostsDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@AllArgsConstructor
@Service
public class PostDTOMapper implements Function<Post, PostsDTO> {
    private final UserDTOMapper userDTOMapper;


    @Override
    public PostsDTO apply(Post post) {
        return new PostsDTO(
                post.getPostId(),
                post.getTitle(),
                post.getCategory(),
                post.getUrl(),
                post.getPoints(),
                post.getCreatedAt(),
                post.getComments().size(),
                userDTOMapper.apply(post.getUser())
        );
    }
}
