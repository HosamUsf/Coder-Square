package com.codersquare.mapper;

import com.codersquare.comment.Comment;
import com.codersquare.response.CommentDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@AllArgsConstructor
@Service
public class CommentDTOMapper implements Function<Comment, CommentDTO> {
    private final UserDTOMapper userDTOMapper;

    @Override
    public CommentDTO apply(Comment comment) {
        return new CommentDTO(
                comment.getUser().getUserId(),
                comment.getPost().getPostId(),
                comment.getCommentId(),
                comment.getText(),
                comment.getPoints(),
                comment.getCreatedAt()
                );
    }


}
