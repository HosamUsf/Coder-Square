package com.codersquare.votes;


import com.codersquare.request.UpdateVoteRequest;
import com.codersquare.response.UpdateVoteResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
public class VoteController {

    private final VoteService voteService;

    @PutMapping("/vote")
    public ResponseEntity<UpdateVoteResponse> updateVote(@RequestBody UpdateVoteRequest request) {
        return voteService.processVote(request.userId(), request.postId(), request.voteType());
    }
}
