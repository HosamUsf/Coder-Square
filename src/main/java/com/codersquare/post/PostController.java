package com.codersquare.post;


import com.codersquare.request.CreatePostRequest;
import com.codersquare.response.CreatePostResponse;
import com.codersquare.response.DeleteEntityResponse;
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

    @GetMapping("/post")
    public Page<Post> getAll(@RequestParam(defaultValue = "" + 1, required = false) int page) {
        return postService.findAll(page);
    }

    @GetMapping("/posts")
    public List<Post> getAll() {
        return postService.findAll();
    }

    @PostMapping("/posts/new")
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody @Valid CreatePostRequest request) {
        return postService.createPost(request);
    }


    //TODO:make it user name
    @GetMapping("/post/{userId}")
    public List<Post> findAllByUserId(@PathVariable Long userId) {
        return postService.findPostWithUserId(userId);
    }

    @GetMapping("/posts/{postId}")
    public Post findAllByPostId(@PathVariable Long postId) {
        return postService.findPosById(postId);
    }

    @DeleteMapping("/posts/{PostId}")
    public ResponseEntity<DeleteEntityResponse> deletePost(@PathVariable Long PostId) {
        return postService.deletePost(PostId);
    }

}
