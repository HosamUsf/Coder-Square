package com.codersquare.comment;


import com.codersquare.mapper.CommentDTOMapper;
import com.codersquare.post.PostRepository;
import com.codersquare.request.CreateCommentRequest;
import com.codersquare.response.CommentDTO;
import com.codersquare.response.CreateCommentResponse;
import com.codersquare.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommenRepository commenRepository;
    private final CommentDTOMapper commentDTOMapper;


    public List<CommentDTO> getAllComment() {
        return commenRepository.findAll().stream().map(commentDTOMapper).collect(Collectors.toList());
    }

    public ResponseEntity<CreateCommentResponse> createComment(CreateCommentRequest request) {
        try {
            Comment comment = new Comment(request.text());
            comment.setUser(userRepository.getReferenceById((long) request.userId()));
            comment.setPost(postRepository.getReferenceById((long) request.postId()));
            commenRepository.save(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(CreateCommentResponse.success(
                    "Comment Created successfully", request.userId() + "", request.postId() + "", String.valueOf(comment.getCommentId())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CreateCommentResponse.error(e.getMessage()));
        }
    }
}
