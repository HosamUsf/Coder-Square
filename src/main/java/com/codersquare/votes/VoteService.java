package com.codersquare.votes;


import com.codersquare.exceptions.UserAlreadyVoteException;
import com.codersquare.post.Post;
import com.codersquare.post.PostRepository;
import com.codersquare.response.UpdateVoteResponse;
import com.codersquare.user.User;
import com.codersquare.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VoteService {

    public static final String DOWNVOTE = "downvote";
    public static final String UPVOTE = "upvote";
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    /**
     * Processes a vote for a post.
     *
     * @param userId   The ID of the user casting the vote.
     * @param postId   The ID of the post on which the vote is cast.
     * @param voteType The type of vote, e.g., "upvote" or "downvote".
     * @return ResponseEntity containing the result of the vote processing.
     */
    public ResponseEntity<UpdateVoteResponse> processVote(long userId, long postId, String voteType) {
        try {
            // Check if the user and post exist
            checkIfUserExists(userId);
            checkIfPostExists(postId);

            // Process the vote for the post
            long voteId = processPostVote(userId, postId, voteType);

            // Return success response
            return ResponseEntity.status(HttpStatus.OK)
                    .body(UpdateVoteResponse.success("Vote processed successfully",
                            new UpdateVoteResponse.VoteData(String.valueOf(userId),
                                    String.valueOf(postId), String.valueOf(voteId))));
        } catch (UserAlreadyVoteException e) {
            // Return conflict response if the user has already voted
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(UpdateVoteResponse.error(e.getMessage(),
                            new UpdateVoteResponse.ErrorData("USER_ALREADY_VOTED", e.getMessage())));
        } catch (EntityNotFoundException e) {
            // Return bad request response if user or post not found
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(UpdateVoteResponse.error(e.getMessage(),
                            new UpdateVoteResponse.ErrorData("ENTITY_NOT_FOUND", e.getMessage())));
        } catch (Exception e) {
            // Return internal server error response for other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(UpdateVoteResponse.error("Internal Server Error",
                            new UpdateVoteResponse.ErrorData("INTERNAL_SERVER_ERROR", e.getMessage())));
        }
    }

    /**
     * Processes a vote for a post, updating post points and saving the vote.
     *
     * @param userId   The ID of the user casting the vote.
     * @param postId   The ID of the post on which the vote is cast.
     * @param voteType The type of vote, e.g., "upvote" or "downvote".
     * @return The ID of the processed vote.
     */
    private long processPostVote(long userId, long postId, String voteType) {
        // Retrieve existing vote and post
        Vote vote = voteRepository.findVoteByUserIdAndPostId(userId, postId).orElse(null);
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new EntityNotFoundException("Post with Id " + postId + " Not Found"));

        // Retrieve user for better consistency check
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("User with Id " + userId + " Not Found"));

        // Process the vote based on its existence
        if (vote == null) {
            if (UPVOTE.equals(voteType)) {
                post.setPoints(post.getPoints() + 1);
            } else if (DOWNVOTE.equals(voteType)) {
                post.setPoints(post.getPoints() - 1);
            }
            // Create a new vote entity
            vote = new Vote(user, post, voteType);
        } else {
            // Check for duplicate vote type
            if (vote.getVoteType().equals(voteType)) {
                throw new UserAlreadyVoteException("User has already voted on this post");
            } else if (vote.getVoteType().equals(DOWNVOTE) && UPVOTE.equals(voteType)) {
                post.setPoints(post.getPoints() + 1);
                vote.setVoteType(UPVOTE);
            } else if (vote.getVoteType().equals(UPVOTE) && DOWNVOTE.equals(voteType)) {
                post.setPoints(post.getPoints() - 1);
                vote.setVoteType(DOWNVOTE);
            }
        }

        // Save changes to the post and the vote
        postRepository.save(post);
        voteRepository.save(vote);

        // Return the ID of the processed vote
        return vote.getVoteId();
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
