package com.codersquare.post;

import com.codersquare.comment.CommenRepository;
import com.codersquare.comment.Comment;
import com.codersquare.exceptions.InvalidUrlException;
import com.codersquare.mapper.PostDTOMapper;
import com.codersquare.request.CreatePostRequest;
import com.codersquare.response.CreatePostResponse;
import com.codersquare.response.DeleteEntityResponse;
import com.codersquare.response.PostDTO;
import com.codersquare.user.UserRepository;
import com.codersquare.votes.Vote;
import com.codersquare.votes.VoteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing posts.
 */

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostDTOMapper postDTOMapper;
    private final VoteRepository voteRepository;


    /**
     * Retrieves a page of posts with pagination.
     *
     * @param page Page number
     * @return Page of posts
     */
    public Page<PostDTO> findAll(int page) {
        return postRepository.findAll(PageRequest.of(page, 20,
                Sort.by("createdAt").descending())).map(postDTOMapper);
    }

    /**
     * Retrieves all posts.
     *
     * @return List of all posts
     */
    public List<PostDTO> findAll() {
        return postRepository.findAll().stream().map(postDTOMapper).collect(Collectors.toList());
    }

    /**
     * Retrieves  post  with a specific ID.
     *
     * @param PostId Post ID
     * @return List of posts associated with the Post ID
     */
    public PostDTO findPosById(Long PostId) {
        return postRepository.findById(PostId).map(postDTOMapper)
                .orElseThrow(() -> new EntityNotFoundException("Post with Id " + PostId + " Not Found"));
    }

    /**
     * Retrieves all posts associated with a specific user ID.
     *
     * @param userId User ID
     * @return List of posts associated with the user ID
     */
    public List<PostDTO> findPostWithUserId(Long userId) {
        return postRepository.findAllByUserId(userId).stream().map(postDTOMapper).collect(Collectors.toList());
    }

    /**
     * Creates a new post based on the provided request.
     *
     * @param request Create post request
     * @return ResponseEntity with the status and response body
     */
    public ResponseEntity<CreatePostResponse> createPost(CreatePostRequest request) {
        try {
            validateUrl(request.url());
            Post post = new Post(request);
            post.setUser(userRepository.getReferenceById((long) request.userId()));
            postRepository.save(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(CreatePostResponse.success(
                    "Post Created successfully", String.valueOf(post.getPostId()))
            );
        } catch (InvalidUrlException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CreatePostResponse.error(
                    e.getMessage(), new CreatePostResponse.ErrorData("INVALID_URL_FORMAT", e.getMessage()))
            );
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CreatePostResponse.error(
                    e.getMessage(), new CreatePostResponse.ErrorData("ENTITY_NOT_FOUND", e.getMessage()))
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CreatePostResponse.error(
                    e.getMessage(), new CreatePostResponse.ErrorData("INTERNAL_SERVER_ERROR", e.getMessage()))
            );
        }
    }

    /**
     * Deletes a post based on the provided post ID.
     *
     * @param postId Post ID to be deleted
     * @return ResponseEntity with the status and response body
     */

    // TODO: When adding authentication, check whether the user is the owner of the post.
    @Transactional
    public ResponseEntity<DeleteEntityResponse> deletePost(Long postId) {
        try {
            checkIfPostExists(postId);

            Post post = postRepository.findById(postId).orElse(null);

            if (post != null) {
                List<Vote> votes = voteRepository.findAllByPostId(post.getPostId());
                voteRepository.deleteAll(votes);
            }

            postRepository.deleteById(postId);
            return ResponseEntity.status(HttpStatus.OK).
                    body(DeleteEntityResponse.success("Post", "Id", postId.toString()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(DeleteEntityResponse.error("Post", "Id", postId.toString()));
        }
    }

    /**
     * Validates the format of the provided URL.
     *
     * @param url URL to validate
     * @throws InvalidUrlException if the URL is null, empty, or has an invalid format
     */
    private void validateUrl(String url) {
        if (url == null || url.isEmpty()) {
            throw new InvalidUrlException("URL is null or empty");
        }
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new InvalidUrlException("Invalid URL format");
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



