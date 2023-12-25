package com.codersquare.comment;

import com.codersquare.request.CreateCommentRequest;
import com.codersquare.response.CommentDTO;
import com.codersquare.response.CreateCommentResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;



    @GetMapping("/")
    private List<CommentDTO> getAll(){
        return commentService.getAllComment();
    }

    @PostMapping("/new")
    public ResponseEntity<CreateCommentResponse> createComment(@Valid @RequestBody CreateCommentRequest request) {
        return commentService.createComment(request);
    }
}
