package com.codersquare.post;


import com.codersquare.request.CreatePostRequest;
import com.codersquare.response.CreatePostResponse;
import com.codersquare.response.DeleteEntityResponse;
import com.codersquare.response.PostsDTO;
import com.codersquare.response.SinglePostDTO;
import com.codersquare.sort.SortService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class PostController {
    private PostService postService;
    private SortService sortService;

    @GetMapping("/posts")
    public Page<PostsDTO> getAll(@RequestParam(defaultValue = "" + 0, required = false) int page) {
        return postService.findAll(page);
    }

    /*public Page<Post> getAllSorted(@RequestParam(defaultValue = "" + 1, required = false) int page,
                                   @RequestParam(defaultValue = "",required = false) String type) {
        return switch (type != null ? type.toLowerCase() : "") {
            case "recent" -> sortService.getPostsSortedByRecent(page);
            case "popular" -> sortService.getPostsSortedByPopularity(page);
            case "", "null" -> sortService.getHotPosts(page);
            default -> throw new IllegalArgumentException("Invalid sorting type: " + type);
        };
    }*/

    @GetMapping("/post")
    public List<PostsDTO> getAll() {
        return postService.findAll();
    }

    @PostMapping("/posts/newPost")
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody @Valid CreatePostRequest request) {
        return postService.createPost(request);
    }


    @GetMapping("/posts/{postId}")
    public SinglePostDTO findAllByPostId(@PathVariable Long postId) {
        return postService.findPostById(postId);
    }

    @GetMapping("posts/new")
    public Page<PostsDTO> getPostsSortedByRecent(@RequestParam(defaultValue = "" + 0, required = false) int page) {
        return sortService.getPostsSortedByRecent(page);
    }

    @GetMapping("posts/popular")
    public Page<PostsDTO> getPostsSortedByPopularity(@RequestParam(defaultValue = "" + 0, required = false) int page) {
        return sortService.getPostsSortedByPopularity(page);
    }


    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<DeleteEntityResponse> deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }

}
