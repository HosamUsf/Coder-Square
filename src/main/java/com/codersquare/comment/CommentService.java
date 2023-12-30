package com.codersquare.comment;


import com.codersquare.mapper.CommentDTOMapper;
import com.codersquare.post.PostRepository;
import com.codersquare.request.CreateCommentRequest;
import com.codersquare.response.CommentDTO;
import com.codersquare.response.CreateCommentResponse;
import com.codersquare.response.DeleteEntityResponse;
import com.codersquare.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing comments.
 */
@Service
@AllArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommenRepository commenRepository;
    private final CommentDTOMapper commentDTOMapper;

    /**
     * Retrieves a list of all comments.
     *
     * @return List of CommentDTO representing all comments
     */
    public List<CommentDTO> getAllComment() {
        return commenRepository.findAll().stream().map(commentDTOMapper).toList();
    }

    /**
     * Creates a new comment based on the provided request.
     *
     * @param request Create comment request
     * @return ResponseEntity with the status and response body
     */
    @Transactional
    public ResponseEntity<CreateCommentResponse> createComment(CreateCommentRequest request) {
        try {
            checkIfUserExists(request.userId());
            checkIfPostExists(request.postId());
            Comment comment = new Comment(request.text());
            comment.setUser(userRepository.getReferenceById((long) request.userId()));
            comment.setPost(postRepository.getReferenceById((long) request.postId()));
            commenRepository.save(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(CreateCommentResponse.success(
                    "Comment Created successfully", String.valueOf(request.userId()), String.valueOf(request.postId()), String.valueOf(comment.getCommentId())));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
                    (CreateCommentResponse.error(e.getMessage(),
                            new CreateCommentResponse.ErrorData("ENTITY_NOT_FOUND", e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body
                    (CreateCommentResponse.error(e.getMessage(),
                            new CreateCommentResponse.ErrorData("INTERNAL_SERVER_ERROR", e.getMessage())));
        }
    }


    /**
     * Deletes a comment based on the provided comment ID.
     *
     * @param commentId Post ID to be deleted
     * @return ResponseEntity with the status and response body
     */

    // TODO: When adding authentication, check whether the user is the owner of the comment.
    @Transactional
    public ResponseEntity<DeleteEntityResponse> deleteComment(Long commentId) {
        try {
            checkIfCommentExists(commentId);
            commenRepository.deleteById(commentId);
            return ResponseEntity.status(HttpStatus.OK).
                    body(DeleteEntityResponse.success("Comment", "Id", "" + commentId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    DeleteEntityResponse.error("Comment", "Id", "" + commentId));
        }

    }


    /**
     * Checks if a comment exists.
     *
     * @param commentId The ID of the post.
     * @throws EntityNotFoundException if the post does not exist.
     */
    private void checkIfCommentExists(long commentId) {
        if (!commenRepository.existsById(commentId)) {
            throw new EntityNotFoundException("Comment with Id " + commentId + " Not Found");
        }
    }


    /**
     * Checks if a user exists.
     *
     * @param userId The ID of the user.
     * @throws EntityNotFoundException if the user does not exist.
     */
    private void checkIfUserExists(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User with Id " + userId + " Not Found ");
        }
    }

    /**
     * Checks if a post exists.
     *
     * @param postId The ID of the post.
     * @throws EntityNotFoundException if the post does not exist.
     */
    private void checkIfPostExists(long postId) {
        if (!postRepository.existsByPostId(postId)) {
            throw new EntityNotFoundException("Post with Id " + postId + " Not Found ");
        }
    }
}


