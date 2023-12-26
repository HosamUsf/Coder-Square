package com.codersquare.mapper;

import com.codersquare.post.Post;
import com.codersquare.response.PostDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostDTOMapper implements Function<Post, PostDTO> {
    private final UserDTOMapper userDTOMapper;
    private final CommentDTOMapper commentDTOMapper;

    @Override
    public PostDTO apply(Post post) {
        return new PostDTO(
                post.getPostId(),
                post.getTitle(),
                post.getCategory(),
                post.getUrl(),
                post.getPoints(),
                post.getCreatedAt(),
                String.valueOf(post.getComments().size()),
                userDTOMapper.apply(post.getUser()),
                post.getComments().stream().map(commentDTOMapper::apply).collect(Collectors.toList())

        );
    }
}