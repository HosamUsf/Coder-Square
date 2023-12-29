package com.codersquare.likes;

import com.codersquare.request.LikePostRequest;
import com.codersquare.response.LikePostResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/like")
    public ResponseEntity<LikePostResponse> likePost(@Valid @RequestBody LikePostRequest request) {
        return likeService.likePost(request);
    }
}
