package com.codersquare.likes;

import com.codersquare.mapper.PostDTOMapper;
import com.codersquare.post.PostRepository;
import com.codersquare.request.LikePostRequest;
import com.codersquare.response.LikePostResponse;
import com.codersquare.response.PostsDTO;
import com.codersquare.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Service class for managing likes on posts.
 */
@Service
@AllArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostDTOMapper postDTOMapper;

    @Transactional

    public List<PostsDTO> getUserLikes(String userName) {
        checkIfUserExists(userName);
        return likeRepository.findPostsByUserName(userName).stream().map(postDTOMapper).toList();
    }

    /**
     * Likes or unlikes a post based on the provided request.
     *
     * @param request Like post request
     * @return ResponseEntity with the status and response body
     */
    public ResponseEntity<LikePostResponse> likePost(LikePostRequest request) {
        try {
            // Check if the post exists
            checkIfPostExists(request.postId());

            // Check if the user exists
            checkIfUserExists(request.userId());

            // Check if the user has already liked the post
            Like like = likeRepository.findLikeByUser_UserIdAndPost_PostId(request.userId(), request.postId()).orElse(null);

            if (like == null) {
                // User has not liked the post, create a new like
                like = new Like();
                like.setPost(postRepository.getReferenceById(request.postId()));
                like.setUser(userRepository.getReferenceById(request.userId()));
                like.setCreatedAt(LocalDateTime.now());
            } else {
                // User has already liked the post, unlike the post
                likeRepository.deleteById(like.getLikeId());
                return ResponseEntity.status(HttpStatus.OK)
                        .body(LikePostResponse.success("Post unliked successfully", "unlike",null,
                                likeRepository.countAllByPost_PostId(request.postId())
                        ));
            }

            // Save the like
            likeRepository.save(like);

            // Return success response for liking the post
            return ResponseEntity.status(HttpStatus.OK)
                    .body(LikePostResponse.success("Post liked successfully", "liked",
                            new LikePostResponse.LikeData(request.userId(), request.postId()),
                            likeRepository.countAllByPost_PostId(request.postId())
                    ));
        } catch (EntityNotFoundException e) {
            // Return not found response if the post or user is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    LikePostResponse.error("Failed to like post", new LikePostResponse.ErrorData("ENTITY_NOT_FOUND", e.getMessage())
                    )
            );
        } catch (Exception e) {
            // Return internal server error response for other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(LikePostResponse.error("Failed to like post",
                            new LikePostResponse.ErrorData("INTERNAL_SERVER_ERROR", e.getMessage())));
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
            throw new EntityNotFoundException("User with Id " + userId + " Not Found");
        }
    }

    /**
     * Checks if a user exists.
     *
     * @param userName The ID of the user.
     * @throws EntityNotFoundException if the user does not exist.
     */
    private void checkIfUserExists(String userName) {
        if (!userRepository.existsByUserName(userName)) {
            throw new EntityNotFoundException("User with Id " + userName + " Not Found ");
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
