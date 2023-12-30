package com.codersquare.mapper;

import com.codersquare.post.Post;
import com.codersquare.response.SinglePostDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@AllArgsConstructor
@Service
public class SinglePostDTOMapper implements Function<Post, SinglePostDTO> {

    private final UserDTOMapper userDTOMapper;
    private final CommentDTOMapper commentDTOMapper;
    @Override
    public SinglePostDTO apply(Post post) {
        return new SinglePostDTO(
                post.getPostId(),
                post.getTitle(),
                post.getCategory(),
                post.getUrl(),
                post.getPoints(),
                post.getCreatedAt(),
                post.getComments().size(),
                userDTOMapper.apply(post.getUser()),
                post.getComments().stream().map(commentDTOMapper).toList()

        );
    }
}


