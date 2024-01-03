package com.codersquare.votes;


import com.codersquare.comment.Comment;
import com.codersquare.post.Post;
import com.codersquare.request.UpdateVoteRequest;
import com.codersquare.response.UpdateVoteResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
public class VoteController {

    private final VoteService voteService;


    @PostMapping("posts/vote")
    public ResponseEntity<UpdateVoteResponse> voteOnPost(@RequestBody UpdateVoteRequest request) {
        return voteService.processVotes(request.userId(), request.entityId(), Post.class, request.voteType());
    }


    @PostMapping("comments/vote")
    public ResponseEntity<UpdateVoteResponse> voteOnComment(@RequestBody UpdateVoteRequest request) {
        return voteService.processVotes(request.userId(), request.entityId(), Comment.class, request.voteType());
    }

}
