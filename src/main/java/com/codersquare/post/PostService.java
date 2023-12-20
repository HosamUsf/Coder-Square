package com.codersquare.post;

import com.codersquare.exceptions.InvalidUrlException;
import com.codersquare.request.CreatePostRequest;
import com.codersquare.response.CreatePostResponse;
import com.codersquare.response.DeleteEntityResponse;
import com.codersquare.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * Retrieves a page of posts with pagination.
     *
     * @param page Page number
     * @return Page of posts
     */
    public Page<Post> findAll(int page) {
        return postRepository.findAll(PageRequest.of(page, 20));
    }

    /**
     * Retrieves all posts.
     *
     * @return List of all posts
     */
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    /**
     * Retrieves  post  with a specific ID.
     *
     * @param PostId Post ID
     * @return List of posts associated with the Post ID
     */
    public Post findPosById(Long PostId) {
        return postRepository.findById(PostId)
                .orElseThrow(() -> new EntityNotFoundException("Post with Id " + PostId + " Not Found"));
    }

    /**
     * Retrieves all posts associated with a specific user ID.
     *
     * @param userId User ID
     * @return List of posts associated with the user ID
     */
    public List<Post> findPostWithUserId(Long userId) {
        return postRepository.findAllByUserId(userId);
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
                    e.getMessage())
            );
        }
    }

    /**
     * Deletes a post based on the provided post ID.
     *
     * @param postId Post ID to be deleted
     * @return ResponseEntity with the status and response body
     */
    public ResponseEntity<DeleteEntityResponse> deletePost(Long postId) {
        try {
            checkIfPostExists(postId);
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
     * Checks if a post with the given post ID exists.
     *
     * @param postId Post ID to check
     * @throws EntityNotFoundException if the post does not exist
     */
    private void checkIfPostExists(Long postId) {
        if (!postRepository.existsByPostId(postId)) {
            throw new EntityNotFoundException();
        }
    }


}
